package ua.nure.decisionsupportsystem.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ua.nure.decisionsupportsystem.annotations.ValidPassword;
import ua.nure.decisionsupportsystem.entity.base.BaseEntity;
import ua.nure.decisionsupportsystem.entity.enums.UserType;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "dss_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity implements UserDetails {

    private LocalDate registrationDate;

    @Size(min = 4, message = "Username must be at least 4 characters long")
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
