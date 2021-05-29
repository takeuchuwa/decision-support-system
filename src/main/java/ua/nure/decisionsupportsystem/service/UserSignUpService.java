package ua.nure.decisionsupportsystem.service;

import ua.nure.decisionsupportsystem.entity.dto.UserDto;

public interface UserSignUpService {

    boolean signUpUser(UserDto userDto);

    boolean confirmUser(String confirmationToken);
}
