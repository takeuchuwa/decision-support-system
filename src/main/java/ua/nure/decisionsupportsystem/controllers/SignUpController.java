package ua.nure.decisionsupportsystem.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.nure.decisionsupportsystem.entity.dto.UserDto;

import javax.validation.Valid;

@Controller
@RequestMapping("/signup")
public class SignUpController {

    @ModelAttribute("user")
    public UserDto user() {
        return new UserDto();
    }

    @GetMapping
    public String showForm() {
        return "signup";
    }

    @PostMapping
    public String submitForm(@Valid @ModelAttribute("user") UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        return "success";
    }

}
