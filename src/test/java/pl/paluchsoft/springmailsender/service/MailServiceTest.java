package pl.paluchsoft.springmailsender.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class MailServiceTest {

    @MockBean
    JavaMailSender emailSender;
    @Autowired
    MailService mailService;

    @Test
    @DisplayName("Should throw IllegalArgumentException when passed null values")
    void testNullChecks() {
        assertThrows(IllegalArgumentException.class, () -> mailService.sendMessage(null, null, null, null));
    }

    @Test
    @DisplayName("Should send simple email")
    void shouldSendSimpleEmail() throws MessagingException {
        String to = "cokolwiek";
        String subject = "cokolwiek";
        String text = "cokolwiek";
        List<File> attachments = List.of(new File("src/test/resources/templates/test-attachments/blabla.jpg"));
        MimeMessage messageMock = mock(MimeMessage.class);
        when(emailSender.createMimeMessage()).thenReturn(messageMock);
        mailService.sendMessage(to, subject, text, attachments);
        verify(emailSender, times(1)).send(messageMock);
        verify(messageMock).setContent(any());
        verify(messageMock).setSubject(subject);
        verify(messageMock).setRecipient(eq(Message.RecipientType.TO), argThat(address -> address.toString().contains(to)));
    }
}