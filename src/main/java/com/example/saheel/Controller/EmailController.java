package com.example.saheel.Controller;

import com.example.saheel.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {


    @Autowired
    private EmailService emailService;

    @GetMapping("/send")
    public ResponseEntity<String> sendTestEmail() {
        emailService.sendEmail("receiver_email@example.com", "Test Subject", "Hello from Spring Boot!");
        return ResponseEntity.ok("Email sent successfully!");
    }
}