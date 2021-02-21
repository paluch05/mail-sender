package pl.paluchsoft.springmailsender.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class MailServiceTest {

    @MockBean
    JavaMailSender emailSender;
    @Autowired
    MailService mailService;

    @Test
    @DisplayName("Should throw IllegalArgumentException when passed null values")
    void testNullChecks() {
        assertThrows(IllegalArgumentException.class, () -> mailService.sendSimpleMessage(null, null, null));
    }

    @Test
    @DisplayName("Should send simple email")
    void shouldSendSimpleEmail() {
        String to = "cokolwiek";
        String subject = "cokolwiek";
        String text = "cokolwiek";
        mailService.sendSimpleMessage(to, subject, text);
        verify(emailSender, times(1)).send(argThat((ArgumentMatcher<SimpleMailMessage>) message -> message.getTo()[0].equals(to) && message.getSubject().equals(subject) && message.getText().equals(text)));
    }
}