package com.example.ooo;

import com.example.ooo.backend.service.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.mail.MessagingException;
import java.time.LocalDateTime;

@SpringBootApplication
@EnableScheduling
@AllArgsConstructor
public class OooApplication {

    private final ScheduleService scheduleService;

    public static void main(String[] args){
        SpringApplication.run(OooApplication.class, args);
    }

    @Scheduled(cron = "0/20 * * * * *")
    void schedule() throws MessagingException {
        scheduleService.schedule();
    }
}
