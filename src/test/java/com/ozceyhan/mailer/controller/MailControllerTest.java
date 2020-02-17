package com.ozceyhan.mailer.controller;

import com.ozceyhan.mailer.model.Mail;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.ozceyhan.mailer.controller.MailController;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class MailControllerTest {

    @Mock
    private MailController mailController;

    /**
     * Test method for
     * {@link com.ozceyhan.mailer.controller.MailController#sendMail(com.ozceyhan.mailer.model.Mail)}.
     * 
     * @throws Exception
     */
    @Test
    public void sendMailTest() throws Exception {
        Mockito.when(mailController.sendMail(Mockito.any(Mail.class))).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        ResponseEntity<?> response = mailController.sendMail(new Mail());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
}