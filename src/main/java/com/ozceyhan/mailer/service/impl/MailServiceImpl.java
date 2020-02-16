package com.ozceyhan.mailer.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.ozceyhan.mailer.model.Mail;
import com.ozceyhan.mailer.service.interfc.MailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    public JavaMailSender emailSender;

    public void sendMail(Mail mail) {
        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setTo(mail.getTo());
            helper.setSubject(mail.getSubject());
            helper.setText(mail.getText());
            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

}