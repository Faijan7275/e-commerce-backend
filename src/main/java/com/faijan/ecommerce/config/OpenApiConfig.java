package com.faijan.ecommerce.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("E-Commerce Backend API")
                        .version("1.0.0")
                        .description("REST API for a Spring Boot e-commerce backend with JWT authentication")
                        .contact(new Contact()
								.name("Md Faijan Alam")
								.email("alamfaijan@xyz.com"))
                        );
    }
}
