package com.ozceyhan.mailer.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.ozceyhan.mailer.model.Mail;
import com.ozceyhan.mailer.service.interfc.MailService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class MailConsumer {

    private final Logger logger = LoggerFactory.getLogger(MailConsumer.class);

    @Autowired
    MailService mailService;

    @KafkaListener(topics = "${spring.kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(Mail mail) {
        logger.info("Consuming mail template...");
        mailService.sendMail(mail);
    }
}