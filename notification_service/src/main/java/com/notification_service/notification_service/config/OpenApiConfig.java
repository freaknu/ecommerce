package com.notification_service.notification_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPIConfiguration() {
        return new OpenAPI()
                .info(new Info().title("Notification Service")
                        .description("Notification Service for Ecommerce Backend")
                        .contact(new Contact().email("pk2239.29.jnv@gmail.com")
                                .name("Prabhat Kumar")));
    }
}
