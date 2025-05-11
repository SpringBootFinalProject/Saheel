package com.example.saheel.Controller;

import com.example.saheel.Service.WhatsAppNotifications;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final WhatsAppNotifications whatsAppNotifications;

    @PostMapping("/send-hello")
    public ResponseEntity<String> sendHelloMessage(@RequestParam String phoneNumber) {
        try {
            // Send the "Hello" message to the provided phone number
            whatsAppNotifications.sendHelloMessage(phoneNumber);
            return ResponseEntity.ok("Message sent successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to send message: " + e.getMessage());
        }
    }
}
