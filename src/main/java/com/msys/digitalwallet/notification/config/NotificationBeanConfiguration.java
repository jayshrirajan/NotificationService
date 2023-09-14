package com.msys.digitalwallet.notification.config;

import com.msys.digitalwallet.notification.common.exception.BusinessException;
import com.msys.digitalwallet.notification.common.exception.enums.ExceptionType;
import com.msys.digitalwallet.notification.enums.ServiceName;
import com.msys.digitalwallet.notification.fake.FakeNotificationService;
import com.msys.digitalwallet.notification.integration.IntegrationClient;
import com.msys.digitalwallet.notification.twilio.TwilioService;
import com.msys.digitalwallet.notification.twofactor.TwoFactorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class NotificationBeanConfiguration {

    @Autowired
    NotificationBeanProperties notificationBeanProperties;

    @Primary
    @Bean
    public IntegrationClient integrationClient(){
        if(notificationBeanProperties.getServiceName()== ServiceName.TWILIO)
            return new TwilioService();
        else if(notificationBeanProperties.getServiceName()== ServiceName.TWOFACTOR)
            return new TwoFactorService();
        else  if(notificationBeanProperties.getServiceName()== ServiceName.FAKE)
            return new FakeNotificationService();
        else
            throw new BusinessException(ExceptionType.INVALID_CONFIGURATION
                    ,notificationBeanProperties.getServiceName()+" is not configured.");
    }

}
