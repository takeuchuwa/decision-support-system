package ua.nure.decisionsupportsystem.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/profile")
public class ProfileController {


    @GetMapping
    public String showProfile(HttpServletRequest request) {
        return request.isUserInRole("ROLE_EMPLOYEE")
                ? "profile/employee-profile"
                : "profile/hr-profile";
    }
}
