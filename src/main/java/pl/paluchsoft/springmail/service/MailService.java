package pl.paluchsoft.springmail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import pl.paluchsoft.springmail.Mail;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.io.*;
import freemarker.template.Configuration;
import java.util.Map;

@Service("mailService")
    public class MailService {
        private final JavaMailSender mailSender;
//        @Autowired
        Configuration bean;

        @Autowired
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
                mail.setMailContent(getContentFromTemplate(mail.getModel()));
                helper.setText(mail.getMailContent(), true);
                helper.addAttachment("06.JPEG", new ClassPathResource("06.JPEG"));
                helper.addAttachment("walentynki.pdf", new ClassPathResource("walentynki.pdf"));
                mailSender.send(mimeMessage);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }



        public String getContentFromTemplate(Map<String, Object> model) {
            StringBuilder content = new StringBuilder();
            try {
//                Template template = new Template("template.txt", new StringReader(), bean);
//                content.append(FreeMarkerTemplateUtils.processTemplateIntoString(template, model));
                content.append(FreeMarkerTemplateUtils
                        .processTemplateIntoString(bean.getTemplate("template.txt"), model));
            } catch (Exception e) {
                e.printStackTrace();
            } return content.toString();
        }

        public void addAtt(String file) throws IOException, MessagingException {
            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            attachmentBodyPart.attachFile(new File("C:/users/Anusia/Pulpit/zalaczniki"));
        }
    }