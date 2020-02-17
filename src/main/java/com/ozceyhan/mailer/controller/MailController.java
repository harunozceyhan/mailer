package com.ozceyhan.mailer.controller;

import javax.validation.Valid;

import com.ozceyhan.mailer.model.Mail;
import com.ozceyhan.mailer.producer.MailProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private MailProducer mailProducer;

    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    @Operation(description = "Sends Mail to Kafka Producer", responses = {
            @ApiResponse(responseCode = "200", description = "Mail Sent to Kafka Producer") })
    public ResponseEntity<?> sendMail(@Valid @RequestBody Mail mail) {
        mailProducer.sendMessage(mail);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}