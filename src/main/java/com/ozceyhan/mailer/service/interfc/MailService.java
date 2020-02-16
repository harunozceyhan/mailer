package com.ozceyhan.mailer.service.interfc;

import java.io.IOException;

import javax.mail.MessagingException;

import com.ozceyhan.mailer.model.Mail;

public interface MailService {
    void sendMail(Mail mail) throws MessagingException, IOException;

    void recover(Throwable t);
}