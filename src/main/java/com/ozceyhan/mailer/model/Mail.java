package com.ozceyhan.mailer.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Mail {
    String to;
    String subject;
    String text;
    String attachmentUri;
}