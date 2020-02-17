package com.ozceyhan.mailer.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.ozceyhan.mailer.model.Mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class MailProducer {

    private static final Logger logger = LoggerFactory.getLogger(MailProducer.class);

    @Value("${spring.kafka.topic}")
    public String topic;

    @Autowired
    private KafkaTemplate<String, Mail> kafkaTemplate;

    /**
     * Produces mail object for kafka and sends object to Kafka instance.
     *
     * @param mail Mail
     */
    public void sendMessage(Mail mail) {
        logger.info("Producing mail: " + mail.toString());
        this.kafkaTemplate.send(topic, mail);
    }
}