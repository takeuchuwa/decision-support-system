package ua.nure.decisionsupportsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.nure.decisionsupportsystem.annotations.ValidPassword;
import ua.nure.decisionsupportsystem.entity.common.enums.UserType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    @Size(min = 4, message = "Username must be at least 4 characters long")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Username can contain only english letters and numbers")
    private String username;

    @ValidPassword()
    private String password;

    @NotBlank(message = "First name couldn't be empty")
    private String firstName;

    @NotBlank(message = "Last name couldn't be empty")
    private String lastName;

    @Email(message = "Not valid email")
    @NotBlank(message = "Email couldn't be empty")
    private String email;

    @Pattern(regexp = "^\\+?3?8?(0[\\s\\.-]?\\d{2}[\\s\\.-]?\\d{3}[\\s\\.-]?\\d{2}[\\s\\.-]?\\d{2})$",
            message = "Phone is not valid")
    private String phoneNumber;

    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "Please select your type")
    private UserType userType;
}
