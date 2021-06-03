package ua.nure.decisionsupportsystem.service.impl;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import ua.nure.decisionsupportsystem.entity.User;
import ua.nure.decisionsupportsystem.repositories.UserRepository;
import ua.nure.decisionsupportsystem.service.ContactService;
import ua.nure.decisionsupportsystem.service.EmailSenderService;
import ua.nure.decisionsupportsystem.util.EmailUtil;

import java.security.Principal;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {

    @Setter(onMethod_ = @Autowired)
    private UserRepository userRepository;

    @Setter(onMethod_ = @Autowired)
    private EmailSenderService emailSenderService;

    @Setter(onMethod_ = @Autowired)
    private EmailUtil emailUtil;

    @Override
    public void sendEmail(Long id) {
        Optional<User> contactUser = userRepository.findById(id);
        contactUser.ifPresent(employeeUser -> {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(employeeUser.getEmail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setText("Hello, " + employeeUser.getFirstName() + " " + employeeUser.getLastName() + "\n\n" +
                    emailUtil.getContactMessage());

            emailSenderService.sendEmail(mailMessage);
        });
    }
}
