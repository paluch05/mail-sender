package pl.paluchsoft.springmail.service;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pl.paluchsoft.springmail.AppConfig;
import pl.paluchsoft.springmail.Mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.sql.PreparedStatement;
import java.util.Objects;

@Service
    public class MailService {
        private final JavaMailSender mailSender;

        public MailService(JavaMailSender mailSender) {
            this.mailSender = mailSender;
        }

        public void sendMail(Mail mail) {
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            try {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                helper.setFrom(mail.getMailFrom());
                helper.setTo(mail.getMailTo());
                helper.setSubject(mail.getMailSubject());
                mail.setMailContent(AppConfig.getContentFromTemplate(mail.getModel()));
                helper.setText(mail.getMailContent(), true);
                FileSystemResource file = new FileSystemResource("C:/users/Anusia/Pulpit/zalaczniki");
                helper.addAttachment(Objects.requireNonNull(file.getFilename()), file);
            } catch (MessagingException e) {
               throw new MailParseException(e);
            }
                mailSender.send(mimeMessage);
        }

        public void addAtt(String file) throws IOException, MessagingException {
            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            attachmentBodyPart.attachFile(new File("C:/users/Anusia/Pulpit/zalaczniki"));
        }
    }