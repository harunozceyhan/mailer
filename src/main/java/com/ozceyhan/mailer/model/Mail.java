package com.ozceyhan.mailer.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mail {

    @NotNull
    @Email
    String to;
    @NotNull
    String subject;
    @NotNull
    String text;

    String attachmentUri;
}