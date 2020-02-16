package com.ozceyhan.mailer.service.interfc;

import javax.mail.MessagingException;

import com.ozceyhan.mailer.model.Mail;

public interface MailService {
    void sendMail(Mail mail) throws MessagingException;

    void recover(Throwable t);
}