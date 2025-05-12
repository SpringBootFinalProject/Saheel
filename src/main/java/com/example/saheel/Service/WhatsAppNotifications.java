package com.example.saheel.Service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WhatsAppNotifications {

    private static final String TWILIO_SANDBOX_NUMBER = "whatsapp:+14155238886";

    public void sendHelloMessage(String phoneNumber) {
        try {
            PhoneNumber to = new PhoneNumber("whatsapp:" + phoneNumber);
            PhoneNumber from = new PhoneNumber(TWILIO_SANDBOX_NUMBER);

            Message.creator(to, from, "Hello from Saheel 🐎").create();

        } catch (Exception e) {
            System.out.println("Failed to send message: " + e.getMessage());
            throw new RuntimeException("Failed to send message: " + e.getMessage());
        }
    }


}


