package com.msys.digitalwallet.notification.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "twilio.auth")
@Data
public class Twilio {

    public String accountSid;

    public String authToken;

    public String sid;

    public String sendGridId;

    public String senderEmail;

}
