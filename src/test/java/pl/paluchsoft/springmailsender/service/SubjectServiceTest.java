package pl.paluchsoft.springmailsender.service;

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
class SubjectServiceTest {

    @MockBean
    MailController mailController;
    @Autowired
    SubjectService subjectService;

    @Test
    @DisplayName("Should throw IllegalArgumentException when passed null values")
    void testNullChecks() {
        assertThrows(IllegalArgumentException.class, () -> subjectService.getSubject(null));
    }

    @Test
    @DisplayName("Should read subject from file")
    void shouldReadSubjectFromFile() throws IOException {
        String name = "basic";
        String subject = subjectService.getSubject(name);
        assertEquals("This is my test subject", subject);
    }

}