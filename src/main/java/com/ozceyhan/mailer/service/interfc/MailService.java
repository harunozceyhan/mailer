package com.ozceyhan.mailer.service.interfc;

import com.ozceyhan.mailer.model.Mail;

public interface MailService {
    void sendMail(Mail mail);
}