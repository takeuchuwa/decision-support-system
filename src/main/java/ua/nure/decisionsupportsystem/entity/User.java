package ua.nure.decisionsupportsystem.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ua.nure.decisionsupportsystem.entity.base.BaseEntity;
import ua.nure.decisionsupportsystem.entity.enums.UserStatuses;
import ua.nure.decisionsupportsystem.entity.enums.UserType;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "dss_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity implements UserDetails {

    private LocalDate registrationDate;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    @Enumerated(value = EnumType.STRING)
    private UserType userType;

    @Enumerated(value = EnumType.STRING)
    private UserStatuses userStatus;

    @OneToOne
    private EmployeeInformation employeeInformation;

    @Transient
    private Double employeeWorth;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + userType));
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
        return UserStatuses.ACTIVE.equals(userStatus);
    }
}
