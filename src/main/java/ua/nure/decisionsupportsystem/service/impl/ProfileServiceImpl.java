package ua.nure.decisionsupportsystem.service.impl;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestParam;
import ua.nure.decisionsupportsystem.entity.EmployeeInformation;
import ua.nure.decisionsupportsystem.entity.EmployeeSkills;
import ua.nure.decisionsupportsystem.entity.Skill;
import ua.nure.decisionsupportsystem.entity.User;
import ua.nure.decisionsupportsystem.entity.dto.SearchDto;
import ua.nure.decisionsupportsystem.entity.dto.elements.SearchSkill;
import ua.nure.decisionsupportsystem.repositories.EmployeeInformationRepository;
import ua.nure.decisionsupportsystem.repositories.EmployeeSkillsRepository;
import ua.nure.decisionsupportsystem.repositories.SkillRepository;
import ua.nure.decisionsupportsystem.repositories.UserRepository;
import ua.nure.decisionsupportsystem.service.EmployeeService;
import ua.nure.decisionsupportsystem.service.ProfileService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Setter(onMethod_ = @Autowired)
    private SkillRepository skillRepository;

    @Setter(onMethod_ = @Autowired)
    private EmployeeInformationRepository employeeInformationRepository;

    @Setter(onMethod_ = @Autowired)
    private UserRepository userRepository;

    @Setter(onMethod_ = @Autowired)
    private EmployeeService employeeService;

    @Setter(onMethod_ = @Autowired)
    private EmployeeSkillsRepository employeeSkillsRepository;


    public void prepareEmployeeProfile(ModelMap map, Principal principal) {
        Optional<User> user = userRepository.findByUsername(
                principal.getName());
        user.ifPresent(u -> {
            Optional<EmployeeInformation> employeeInformation = employeeInformationRepository.findByUser(user.get());
            employeeInformation.ifPresentOrElse(
                    emp -> {
                        map.addAttribute("employeeInformation", emp);
                        map.addAttribute("employeeSkills", employeeSkillsRepository.findAllByEmployeeInformation(emp));
                    },
                    () -> map.addAttribute("employeeInformation", new EmployeeInformation()));
        });
        map.addAttribute("skills", skillRepository.findAll());
        map.addAttribute("employeeSkill", new EmployeeSkills());
    }


    public void prepareHrProfile(ModelMap map) {
        SearchDto searchDto = new SearchDto();
        searchDto.setSkills(new ArrayList<>());
        searchDto.getSkills().add(new SearchSkill());
        map.addAttribute("searchDto", searchDto);
        map.addAttribute("allSkills", skillRepository.findAll());
    }


    public void saveInformation(EmployeeInformation employeeInformation, Principal principal) {
        employeeService.saveInformation(employeeInformation, principal);
    }

    public void addSkill(EmployeeInformation employeeInformation, EmployeeSkills employeeSkills, Principal principal) {
        employeeService.addSkill(employeeInformation, employeeSkills, principal);
    }

    public void deleteSkill(Long id) {
        employeeSkillsRepository.deleteById(id);
    }

    public void addRow(SearchDto searchDto, ModelMap map) {
        searchDto.getSkills().add(new SearchSkill());
        map.addAttribute("allSkills", skillRepository.findAll());
    }

    public void removeRow(SearchDto searchDto, HttpServletRequest req, ModelMap map) {
        final int rowId = Integer.parseInt(req.getParameter("removeRow"));
        searchDto.getSkills().remove(rowId);
        map.addAttribute("allSkills", skillRepository.findAll());
    }

    public List<User> searchUsers(SearchDto searchDto) {
        List<User> users = userRepository.findAllByEmployeeSalaryAndWorkExperience(searchDto.getSalary(),
                searchDto.getWorkExperience());
        List<Skill> skills = searchDto.getSkills().stream().map(SearchSkill::getSkill).collect(Collectors.toList());
        users = users.stream()
                .filter(user -> user
                        .getEmployeeInformation()
                        .getEmployeeSkills()
                        .stream()
                        .map(EmployeeSkills::getSkill)
                        .collect(Collectors.toList())
                        .containsAll(skills))
                .collect(Collectors.toList());
        users.sort((u1, u2) -> {
            u1.setEmployeeWorth(calculateEmployeeWorth(searchDto, u1));
            u2.setEmployeeWorth(calculateEmployeeWorth(searchDto, u2));
            return u2.getEmployeeWorth().compareTo(u1.getEmployeeWorth());
        });
        return users;
    }

    private Double calculateEmployeeWorth(SearchDto searchDto, User user) {
        return ((searchDto.getSalary() - user.getEmployeeInformation().getSalary()) * 0.01) +
                ((user.getEmployeeInformation().getWorkExperience() - searchDto.getWorkExperience()) * 0.5) +
                (user.getEmployeeInformation()
                .getEmployeeSkills()
                .stream()
                .filter(employeeSkill -> searchDto.getSkills()
                        .stream()
                        .map(SearchSkill::getSkill)
                        .collect(Collectors.toList()).contains(employeeSkill.getSkill()))
                .mapToDouble(EmployeeSkills::getSkillLevel)
                .sum() * 1.5);
    }
}
