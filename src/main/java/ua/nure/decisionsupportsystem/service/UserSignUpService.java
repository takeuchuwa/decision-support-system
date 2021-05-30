package ua.nure.decisionsupportsystem.service;

import org.springframework.validation.BindingResult;
import ua.nure.decisionsupportsystem.entity.dto.UserDto;

public interface UserSignUpService {

    boolean signUpUser(UserDto userDto, BindingResult bindingResult);

    boolean confirmUser(String confirmationToken);
}
