package com.ozceyhan.mailer.controller;

import com.ozceyhan.mailer.model.Mail;
import com.ozceyhan.mailer.producer.MailProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MailController {

    @Autowired
    private MailProducer mailProducer;

    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> sendMail(@RequestBody Mail mail) {
        mailProducer.sendMessage(mail);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}