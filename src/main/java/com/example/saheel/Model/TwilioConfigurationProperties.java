package com.example.saheel.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
@ConfigurationProperties(prefix = "com.baeldung.twilio")
@Getter
@Setter
public class TwilioConfigurationProperties {

    @NotBlank
    @Pattern(regexp = "^AC[0-9a-fA-F]{32}$")
    private String accountSid;

    @NotBlank
    private String authToken;

    @NotBlank
    @Pattern(regexp = "^MG[0-9a-fA-F]{32}$")
    private String messagingSid;

    private String fromPhoneNumber;

    private NewArticleNotification newArticleNotification;

    @Getter
    @Setter
    public static class NewArticleNotification {
        @NotBlank
        private String contentSid;
    }

}


