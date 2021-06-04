package ua.nure.decisionsupportsystem.controllers;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ua.nure.decisionsupportsystem.entity.EmployeeInformation;
import ua.nure.decisionsupportsystem.entity.EmployeeSkills;
import ua.nure.decisionsupportsystem.entity.User;
import ua.nure.decisionsupportsystem.entity.dto.SearchDto;
import ua.nure.decisionsupportsystem.service.ContactService;
import ua.nure.decisionsupportsystem.service.ProfileService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    
    @Setter(onMethod_ = @Autowired)
    private ProfileService profileService;

    @Setter(onMethod_ = @Autowired)
    private ContactService contactService;

    @GetMapping
    public String showProfile(HttpServletRequest request, ModelMap map, Principal principal) {
        if (request.isUserInRole("ROLE_EMPLOYEE")) {
            profileService.prepareEmployeeProfile(map, principal);
            return "profile/employee-profile";
        } else {
            profileService.prepareHrProfile(map);
            return "profile/hr-profile";
        }
    }

    @PostMapping("/save")
    public String saveInformation(@ModelAttribute("employeeInformation") EmployeeInformation employeeInformation,
                                  Principal principal) {
        profileService.saveInformation(employeeInformation, principal);
        return "redirect:../profile";
    }

    @PostMapping("/add")
    public String addSkill(@ModelAttribute("employeeInformation") EmployeeInformation employeeInformation,
                           @ModelAttribute("employeeSkill") EmployeeSkills employeeSkills, Principal principal) {
        profileService.addSkill(employeeInformation, employeeSkills, principal);
        return "redirect:../profile";
    }

    @PostMapping("/delete")
    public String deleteSkill(@RequestParam("employeeSkillId") Long id) {
        profileService.deleteSkill(id);
        return "redirect:../profile";
    }

    @RequestMapping(params = {"addRow"})
    public String addRow(final SearchDto searchDto, ModelMap map) {
        profileService.addRow(searchDto,map);
        return "profile/hr-profile";
    }

    @RequestMapping(params={"removeRow"})
    public String removeRow(
            final SearchDto searchDto,
            final HttpServletRequest req, ModelMap map) {
        profileService.removeRow(searchDto, req, map);
        return "profile/hr-profile";
    }

    @PostMapping
    public String search(@ModelAttribute("searchDto") SearchDto searchDto, ModelMap map) {
        List<User> users = profileService.searchUsers(searchDto);
        map.addAttribute("users", users);
        map.addAttribute("searchDto", searchDto);
        return "search/employees";
    }

    @PostMapping(params={"userId"})
    public String contact(@RequestParam("userId") Long id, @ModelAttribute("searchDto") SearchDto searchDto, ModelMap map) {
        contactService.sendEmail(id);
        map.addAttribute("id", id);
        return search(searchDto, map);
    }
}

