package com.example.saheel.Config;

import com.example.saheel.Model.TwilioConfigurationProperties;
import com.twilio.Twilio;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties(TwilioConfigurationProperties.class)
@RequiredArgsConstructor
public class TwilioInitializer implements ApplicationRunner {

    private final TwilioConfigurationProperties twilioConfigurationProperties;

    @Override
    public void run(ApplicationArguments args) {
        String accountSid = twilioConfigurationProperties.getAccountSid();
        String authToken = twilioConfigurationProperties.getAuthToken();
        System.out.println(new BCryptPasswordEncoder().encode("admin123"));

        Twilio.init(accountSid, authToken);
    }
}