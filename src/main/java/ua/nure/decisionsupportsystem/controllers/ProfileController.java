package ua.nure.decisionsupportsystem.controllers;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.nure.decisionsupportsystem.entity.EmployeeInformation;
import ua.nure.decisionsupportsystem.entity.EmployeeSkills;
import ua.nure.decisionsupportsystem.entity.User;
import ua.nure.decisionsupportsystem.repositories.EmployeeInformationRepository;
import ua.nure.decisionsupportsystem.repositories.EmployeeSkillsRepository;
import ua.nure.decisionsupportsystem.repositories.SkillRepository;
import ua.nure.decisionsupportsystem.repositories.UserRepository;
import ua.nure.decisionsupportsystem.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
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
            return "profile/hr-profile";
        }
    }

    @PostMapping
    public String addSkill(@ModelAttribute("employeeInformation") EmployeeInformation employeeInformation,
                           @ModelAttribute("employeeSkill") EmployeeSkills employeeSkills,
                           HttpServletRequest request, ModelMap map, Principal principal) {
        employeeService.addSkill(employeeInformation, employeeSkills, principal);
        return showProfile(request, map, principal);
    }
}
