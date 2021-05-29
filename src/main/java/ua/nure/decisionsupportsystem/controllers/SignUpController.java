package ua.nure.decisionsupportsystem.controllers;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ua.nure.decisionsupportsystem.entity.dto.UserDto;
import ua.nure.decisionsupportsystem.service.UserSignUpService;

import javax.validation.Valid;

@Controller
@RequestMapping("/signup")
public class SignUpController {

    @Setter(onMethod_ = @Autowired)
    private UserSignUpService userSignUpService;

    @ModelAttribute("user")
    public UserDto user() {
        return new UserDto();
    }

    @GetMapping
    public String showForm() {
        return "register/signup";
    }

    @PostMapping
    public String submitForm(@Valid @ModelAttribute("user") UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register/signup";
        }
        if (!userSignUpService.signUpUser(userDto)) {
            bindingResult.addError(new ObjectError("username", "This username already taken"));
            return "register/signup";
        }

        return "register/success";
    }

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public String confirmAccount(@RequestParam("token") String confirmationToken) {
        if (!userSignUpService.confirmUser(confirmationToken)) {
            return "errors/not-found";
        }

        return "register/confirm";
    }
}
