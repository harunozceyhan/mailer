package com.ozceyhan.mailer.service.impl;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.ozceyhan.mailer.model.Mail;
import com.ozceyhan.mailer.service.interfc.FileService;
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

    @Autowired
    public FileService fileService;

    @Value("${spring.mail.username}")
    public String from;

    @Retryable(value = { MessagingException.class,
            IOException.class }, maxAttemptsExpression = "#{${spring.mail.max-attempts}}", backoff = @Backoff(delay = 5000))
    public void sendMail(Mail mail) throws MessagingException, IOException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(mail.getTo());
        helper.setFrom(from);
        helper.setSubject(mail.getSubject());
        helper.setText(mail.getText());
        if (mail.getAttachmentUri() != null) {
            helper.addAttachment(fileService.getFileNameFromUrl(mail.getAttachmentUri()),
                    fileService.getInputStreamSourceOfUrl(mail.getAttachmentUri()));
        }

        emailSender.send(message);
        fileService.deleteAttachmentFile(fileService.getFileNameFromUrl(mail.getAttachmentUri()));
    }

    @Recover
    public void recover(Throwable t) {
        LOGGER.info("Error Class :: " + t.getClass().getName());
    }

}