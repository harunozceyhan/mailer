package com.ozceyhan.mailer.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Mail Sender API").version("0.0.1")
                        .description("Mail Sender API that sends mails using Kafka"))
                .addTagsItem(new Tag().name("mail"));
    }
}