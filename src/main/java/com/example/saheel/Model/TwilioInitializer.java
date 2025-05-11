package com.example.saheel.Model;

import com.twilio.Twilio;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties(TwilioConfigurationProperties.class)
@RequiredArgsConstructor
class TwilioInitializer implements ApplicationRunner {

    private final TwilioConfigurationProperties twilioConfigurationProperties;

    // standard constructor

    @Override
    public void run(ApplicationArguments args) {
        String accountSid = twilioConfigurationProperties.getAccountSid();
        String authToken = twilioConfigurationProperties.getAuthToken();
        Twilio.init(accountSid, authToken);
    }

}