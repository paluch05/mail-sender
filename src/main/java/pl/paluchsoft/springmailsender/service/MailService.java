package pl.paluchsoft.springmailsender.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final Logger logger = LoggerFactory.getLogger(MailService.class);
    private final JavaMailSender emailSender;
    @Value("${mail.from}")
    private String from;
    @Value("${spring.mail.username}")
    private String username;

    public MailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendSimpleMessage(
            String to, String subject, String text) {
        if (to == null || subject == null || text == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        logger.info("username: {}", username);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}
