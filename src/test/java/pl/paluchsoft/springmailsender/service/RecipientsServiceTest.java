package pl.paluchsoft.springmailsender.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import pl.paluchsoft.springmailsender.controller.MailController;
import pl.paluchsoft.springmailsender.model.Recipient;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
class RecipientsServiceTest {

    @MockBean
    MailController mailController;
    @Autowired
    RecipientsService recipientsService;

    @Test
    @DisplayName("Should throw IllegalArgumentException when passed null values")
    void testNullChecks() {
        assertThrows(IllegalArgumentException.class, () -> recipientsService.getRecipients(null));
    }

    @Test
    @DisplayName("Should get list of recipients")
    void shouldGetListOfRecipients() throws IOException {
        String name = "basic";
        List<Recipient> recipients = recipientsService.getRecipients(name);
        assertEquals(List.of(new Recipient("test", "test@testuser.com")), recipients);
    }

    @Test
    @DisplayName("Should get list of recipients from a file with empty lines")
    void shouldGetListOfRecipientsFileWithEmptyLines() throws IOException {
        String name = "basic_with_empty_lines";
        List<Recipient> recipients = recipientsService.getRecipients(name);
        assertEquals(List.of(new Recipient("test", "test@testuser.com"), new Recipient("test", "test2@gmail.com")), recipients);
    }
}