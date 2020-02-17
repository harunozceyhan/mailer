package com.ozceyhan.mailer.service;

import com.ozceyhan.mailer.model.Mail;
import com.ozceyhan.mailer.service.interfc.MailService;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class MailServiceTest {

    @Mock
    private MailService mailService;

    /**
     * Test method for
     * {@link com.ozceyhan.mailer.service.MailService#sendMail(com.ozceyhan.mailer.model.Mail)}.
     * 
     * @throws Exception
     */
    @Test
    public void sendMailTest() throws Exception {
        MailService spy = Mockito.spy(mailService);
        Mockito.doNothing().when(spy).sendMail(Mockito.any(Mail.class));
    }
}