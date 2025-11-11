package com.ai.chatbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class AiChatbotApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiChatbotApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**") // Allow all endpoints under /api
                        .allowedOrigins("http://localhost:3000") // ✅ your frontend port
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // ✅ include OPTIONS
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
