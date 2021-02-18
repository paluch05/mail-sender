package pl.paluchsoft.springmail;

import org.springframework.web.bind.annotation.PostMapping;
import pl.paluchsoft.springmail.service.MailService;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MailController {

    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping(value = "/sendMail")
    public void sendMail() throws MessagingException, IOException {
        Mail mail = new Mail();
        mail.setMailFrom("wesolakulka1@gmail.com");
        mail.setMailTo("recipients.properties");
        mail.setMailSubject("Working on first mailsender app");
        Map<String, Object> model = new HashMap<String, Object>();
            model.put("firstName", "Client");
            model.put("signature", "ggsq.pl");
            mail.setModel(model);
            mailService.sendMail(mail);
        }
    }