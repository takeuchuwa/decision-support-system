package ua.nure.decisionsupportsystem.service.impl;

import lombok.Data;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ua.nure.decisionsupportsystem.service.EmailSenderService;

@Service("emailSenderService")
@Data
public class EmailSenderServiceImpl implements EmailSenderService {

    @Setter(onMethod_ = @Autowired)
    private JavaMailSender javaMailSender;

    @Override
    @Async
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }
}
