package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
import com.example.saheel.Model.User;
import com.example.saheel.Repository.UserRepository;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class WhatsAppNotifications {

    private final UserRepository userRepository;

    private static final String TWILIO_SANDBOX_NUMBER = "whatsapp:+14155238886";


    public void sendHelloMessage(String phoneNumber, String message) {
        try {
            PhoneNumber to = new PhoneNumber("whatsapp:" + phoneNumber);
            PhoneNumber from = new PhoneNumber(TWILIO_SANDBOX_NUMBER);

            Message.creator(to, from, message).create();
        } catch (Exception e) {
            System.out.println("Failed to send message: " + e.getMessage());
            throw new RuntimeException("Failed to send message: " + e.getMessage());
        }
    }

//    public void sendHelloMessage(String phoneNumber, Integer userId) {
//        User admin = userRepository.findUserByIdAndRole(userId, "ADMIN");
//        if (admin == null) {
//            throw new ApiException("Only admins can send WhatsApp messages.");
//        }
//
//        try {
//            PhoneNumber to = new PhoneNumber("whatsapp:" + phoneNumber);
//            PhoneNumber from = new PhoneNumber(TWILIO_SANDBOX_NUMBER);
//
//            Message.creator(to, from,
//                            "مرحباً بك في منصة مهيـل! نتمنى لك وقتاً ممتعاً وتجربة ملهمة في عالم الفروسية.").
//                    create();
//        } catch (Exception e) {
//            System.out.println("Failed to send message: " + e.getMessage());
//            throw new RuntimeException("Failed to send message: " + e.getMessage());
//        }
//    }
}


