package pl.paluchsoft.springmailsender.service;

import freemarker.template.TemplateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import pl.paluchsoft.springmailsender.controller.MailController;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
class TemplateServiceTest {

    @MockBean
    MailController mailController;
    @Autowired
    TemplateService templateService;

    @Test
    @DisplayName("Should throw IllegalArgumentException when passed null values")
    void testNullChecks() {
        assertThrows(IllegalArgumentException.class, () -> templateService.getRenderedTemplate(null, null));
    }

    @Test
    @DisplayName("Should read template from file")
    void readTemplateFromFile() throws IOException, TemplateException {
        String templateName = "basic";
        String recipientName = "test User";
        String renderedTemplate = templateService.getRenderedTemplate(templateName, recipientName);
        assertEquals("Hello, " + recipientName + "!", renderedTemplate);
    }
}