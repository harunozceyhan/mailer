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
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
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

    /**
     * Create MimeMessage object from mail parameter object and send mail. Download
     * file and add attachment if attachmentUri provided Retry with given number of
     * attemps if mail exception thrown.
     * 
     * @param mail Mail
     */

    @Async
    @Retryable(value = {
            Exception.class }, maxAttemptsExpression = "#{${spring.mail.max-attempts}}", backoff = @Backoff(delayExpression = "#{${spring.mail.backoff-delay}}"))
    public void sendMail(Mail mail) {
        MimeMessage message = emailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(mail.getTo());
            helper.setFrom(from);
            helper.setSubject(mail.getSubject());
            helper.setText(mail.getText());
            if (mail.getAttachmentUri() != null) {
                helper.addAttachment(fileService.getFileNameFromUrl(mail.getAttachmentUri()),
                        fileService.getInputStreamSourceOfUrl(mail.getAttachmentUri()));
            }
        } catch (IOException ex) {
            LOGGER.error("IOException has occured while downloading attachment file...", ex);
            ex.printStackTrace();
        } catch (MessagingException ex) {
            LOGGER.error("Messaging Exception has occured while sending mail...", ex);
            ex.printStackTrace();
        }

        emailSender.send(message);
        LOGGER.info("Mail sent successfully...");

        if (mail.getAttachmentUri() != null) {
            fileService.deleteAttachmentFile(fileService.getFileNameFromUrl(mail.getAttachmentUri()));
        }
    }

    @Recover
    public void recover(Throwable t) {
        LOGGER.error("Exception has occured while sending mail with given number of attempts...", t);
    }

}