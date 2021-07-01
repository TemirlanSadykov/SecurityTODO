package com.example.ooo;

import com.example.ooo.backend.service.TodoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.mail.MessagingException;

@SpringBootApplication
public class OooApplication {

    public static void main(String[] args) throws MessagingException {
        TodoService.sendMessage();
        SpringApplication.run(OooApplication.class, args);
    }

}
