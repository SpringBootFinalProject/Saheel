package com.example.saheel.Service;

import com.example.saheel.Model.TwilioConfigurationProperties;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WhatsAppNotifications {

    private final TwilioConfigurationProperties twilioConfigurationProperties;

    public void sendHelloMessage(String phoneNumber) {
        // Twilio account SID and Messaging SID
        String messagingSid = twilioConfigurationProperties.getMessagingSid();

        // WhatsApp formatted phone number
        PhoneNumber toPhoneNumber = new PhoneNumber("whatsapp:" + phoneNumber);

        // Sending the simple "Hello" message
        Message message = Message.creator(
                toPhoneNumber,                              // To phone number in WhatsApp format
                "whatsapp:+14155238886",                     // From phone number (Twilio Sandbox or your Twilio WhatsApp number)
                "Hello"                                      // Message body
        ).create();                                      // Create the message
    }
}
