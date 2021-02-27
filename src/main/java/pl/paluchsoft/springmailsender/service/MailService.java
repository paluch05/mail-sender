package pl.paluchsoft.springmailsender.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;

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

    public void sendMessage(
            String to, String subject, String text, List<File> attachments) throws MessagingException {
        if (to == null || subject == null || text == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        logger.info("username: {}", username);
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);
        for (File attachment : attachments) {
            helper.addAttachment(attachment.getName(), attachment);
        }
        emailSender.send(message);
    }
}
