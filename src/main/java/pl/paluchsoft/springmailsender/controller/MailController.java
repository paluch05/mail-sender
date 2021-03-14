package pl.paluchsoft.springmailsender.controller;

import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.paluchsoft.springmailsender.model.Recipient;
import pl.paluchsoft.springmailsender.service.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
public class MailController {

    Logger logger = LoggerFactory.getLogger(MailController.class);
    private final MailService mailService;
    private final TemplateService templateService;
    private final RecipientsService recipientsService;
    private final SubjectService subjectService;
    private final AttachmentService attachmentService;

    public MailController(MailService mailService, TemplateService templateService,
                          RecipientsService recipientsService, SubjectService subjectService, AttachmentService attachmentService) {
        this.mailService = mailService;
        this.templateService = templateService;
        this.recipientsService = recipientsService;
        this.subjectService = subjectService;
        this.attachmentService = attachmentService;
    }

    @GetMapping("/mail/{template}/{recipients}")
    public void sendMail(@PathVariable("template") String template, @PathVariable("recipients") String recipients)
            throws IOException {
        logger.info("sending email");
        List<Recipient> recipientsList;
        try {
            recipientsList = recipientsService.getRecipients(recipients);
        } catch (FileNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
        for (Recipient recipient : recipientsList) {
            try {
                String renderedTemplate = templateService.getRenderedTemplate(template, recipient.getName());
                String readSubject = subjectService.getSubject(template);
                List<File> attachments = attachmentService.getAttachments(template);
                mailService.sendMessage(recipient.getEmail(), readSubject, renderedTemplate, attachments);
            } catch (TemplateException e) {
                throw new FileNotFoundException("Could not find the template so mail is unable to be sent");
            }
        }
        logger.info("sent email");
    }
}
