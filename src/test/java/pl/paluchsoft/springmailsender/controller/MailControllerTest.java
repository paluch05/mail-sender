package pl.paluchsoft.springmailsender.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@WebMvcTest(value = MailController.class)
@ActiveProfiles("test")
class MailControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    MailController mailController;

    @Test
    @DisplayName("Should call service to send an email")
    void shouldCallServiceToSendAnEmail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/mail/template/recipients"));
        verify(mailController, times(1)).sendMail(anyString(), anyString());
    }
}