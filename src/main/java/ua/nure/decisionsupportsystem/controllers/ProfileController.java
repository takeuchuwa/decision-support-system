package ua.nure.decisionsupportsystem.controllers;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
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

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

@Controller
@RequestMapping("/profile")
public class ProfileController {

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

    @GetMapping
    public String showProfile(HttpServletRequest request, ModelMap map, Principal principal) {
        if (request.isUserInRole("ROLE_EMPLOYEE")) {
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
            return "profile/employee-profile";
        } else {
            SearchDto searchDto = new SearchDto();
            searchDto.setSkills(new ArrayList<>());
            searchDto.getSkills().add(new SearchSkill());
            map.addAttribute("searchDto", searchDto);
            map.addAttribute("allSkills", skillRepository.findAll());
            return "profile/hr-profile";
        }
    }

    @PostMapping("/save")
    public String saveInformation(@ModelAttribute("employeeInformation") EmployeeInformation employeeInformation,
                                  Principal principal) {
        employeeService.saveInformation(employeeInformation, principal);
        return "redirect:../profile";
    }

    @PostMapping("/add")
    public String addSkill(@ModelAttribute("employeeInformation") EmployeeInformation employeeInformation,
                           @ModelAttribute("employeeSkill") EmployeeSkills employeeSkills, Principal principal) {
        employeeService.addSkill(employeeInformation, employeeSkills, principal);
        return "redirect:../profile";
    }

    @PostMapping("/delete")
    public String deleteSkill(@RequestParam("employeeSkillId") Long id) {
        employeeSkillsRepository.deleteById(id);
        return "redirect:../profile";
    }

    @RequestMapping(params = {"addRow"})
    public String addRow(final SearchDto searchDto, ModelMap map) {
        searchDto.getSkills().add(new SearchSkill());
        map.addAttribute("allSkills", skillRepository.findAll());
        return "profile/hr-profile";
    }

    @RequestMapping(params={"removeRow"})
    public String removeRow(
            final SearchDto searchDto,
            final HttpServletRequest req, ModelMap map) {
        final int rowId = Integer.parseInt(req.getParameter("removeRow"));
        searchDto.getSkills().remove(rowId);
        map.addAttribute("allSkills", skillRepository.findAll());
        return "profile/hr-profile";
    }
}

