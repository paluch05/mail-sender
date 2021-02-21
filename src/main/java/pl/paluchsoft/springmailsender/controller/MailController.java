package pl.paluchsoft.springmailsender.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.paluchsoft.springmailsender.service.MailService;

@RestController
public class MailController {

    Logger logger = LoggerFactory.getLogger(MailController.class);
    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @GetMapping("/mail")
    public void sendMail() {
        logger.info("sending email");
        mailService.sendSimpleMessage("annapaluch05@gmail.com", "rheu", "hfeiuw");
        logger.info("sent email");
    }


}
