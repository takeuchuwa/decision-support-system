package ua.nure.decisionsupportsystem.service;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.nure.decisionsupportsystem.entity.User;
import ua.nure.decisionsupportsystem.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserRepositoryUserDetailsService implements UserDetailsService {

    @Setter(onMethod_ = @Autowired)
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found"));
    }
}
