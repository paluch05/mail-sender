package pl.paluchsoft.springmailsender.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import pl.paluchsoft.springmailsender.controller.MailController;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class AttachmentServiceTest {

    @MockBean
    MailController mailController;

    @Autowired
    AttachmentService attachmentService;

    @Test
    @DisplayName("Should throw IllegalArgumentException when passed null value")
    void testNullChecks() {
        assertThrows(IllegalArgumentException.class, () -> attachmentService.getAttachments(null));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when passed empty value")
    void testEmptyChecks() {
        assertThrows(IllegalArgumentException.class, () -> attachmentService.getAttachments(""));
    }

    @Test
    @DisplayName("Should get list with files")
    void shouldGetListWithFiles() {
        String templateName = "test-attachments";
        List<File> attachments = attachmentService.getAttachments(templateName);
        assertEquals(2, attachments.size());
        assertTrue(attachments.stream().anyMatch(f -> f.getName().equals("blabla.jpg")));
        assertTrue(attachments.stream().anyMatch(f -> f.getName().equals("kitty.jpg")));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when file or directory does not exist")
    void shouldThrowExceptionWhenFileOrDirectoryDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> attachmentService.getAttachments("cokolwiek"));
    }
}