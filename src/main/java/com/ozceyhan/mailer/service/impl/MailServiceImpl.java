package com.ozceyhan.mailer.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.ozceyhan.mailer.model.Mail;
import com.ozceyhan.mailer.service.interfc.MailService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailServiceImpl.class);

    @Autowired
    public JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    public String from;

    @Retryable(value = {
            MessagingException.class }, maxAttemptsExpression = "#{${spring.mail.max-attempts}}", backoff = @Backoff(delay = 5000))
    public void sendMail(Mail mail) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(mail.getTo());
        helper.setFrom(from);
        helper.setSubject(mail.getSubject());
        helper.setText(mail.getText());
        emailSender.send(message);
    }

    @Recover
    public void recover(Throwable t) {
        LOGGER.info("Error Class :: " + t.getClass().getName());
    }

}