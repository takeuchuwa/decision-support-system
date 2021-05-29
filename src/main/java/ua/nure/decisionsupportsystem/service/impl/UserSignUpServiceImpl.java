package ua.nure.decisionsupportsystem.service.impl;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.decisionsupportsystem.util.EmailUtil;
import ua.nure.decisionsupportsystem.entity.ConfirmationToken;
import ua.nure.decisionsupportsystem.entity.User;
import ua.nure.decisionsupportsystem.entity.dto.UserDto;
import ua.nure.decisionsupportsystem.entity.enums.UserStatuses;
import ua.nure.decisionsupportsystem.repositories.ConfirmationTokenRepository;
import ua.nure.decisionsupportsystem.repositories.UserRepository;
import ua.nure.decisionsupportsystem.service.EmailSenderService;
import ua.nure.decisionsupportsystem.service.UserSignUpService;

import java.time.LocalDate;
import java.util.Optional;

@Service
@PropertySource("classpath:email.properties")
public class UserSignUpServiceImpl implements UserSignUpService {

    @Setter(onMethod_ = @Autowired)
    private UserRepository userRepository;

    @Setter(onMethod_ = @Autowired)
    private PasswordEncoder passwordEncoder;

    @Setter(onMethod_ = @Autowired)
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Setter(onMethod_ = @Autowired)
    private EmailSenderService emailSenderService;

    @Setter(onMethod_ = @Autowired)
    private EmailUtil emailUtil;

    @Override
    @Transactional
    public boolean signUpUser(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            return false;
        }

        User user = User.builder()
                .registrationDate(LocalDate.now())
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .phoneNumber(userDto.getPhoneNumber())
                .userType(userDto.getUserType())
                .userStatus(UserStatuses.PENDING_ACTIVATION)
                .build();

        userRepository.save(user);

        ConfirmationToken confirmationToken = new ConfirmationToken(user);

        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("Decision Support System");
        mailMessage.setText(emailUtil.getConfirmationMessage() + " " + emailUtil.getConfirmationUrlWithToken() +
                confirmationToken.getConfirmationToken());

        emailSenderService.sendEmail(mailMessage);

        return true;
    }

    @Override
    @Transactional
    public boolean confirmUser(String confirmationToken) {
        Optional<ConfirmationToken> token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        if(token.isPresent()) {
            Optional<User> user = userRepository.findByEmailIgnoreCase(token.get().getUser().getEmail());
            user.ifPresent(activeUser -> {
                activeUser.setUserStatus(UserStatuses.ACTIVE);
                userRepository.save(activeUser);
                confirmationTokenRepository.delete(token.get());
            });
            return true;
        }

        return false;
    }
}
