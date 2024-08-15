package com.omar.chatappback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class ChatAppBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatAppBackApplication.class, args);
    }

}
