package com.msys.digitalwallet.notification.fake;

import com.msys.digitalwallet.notification.common.exception.BusinessException;
import com.msys.digitalwallet.notification.common.exception.enums.ExceptionType;
import com.msys.digitalwallet.notification.enums.Channel;
import com.msys.digitalwallet.notification.integration.FakeClient;
import com.msys.digitalwallet.notification.model.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FakeNotificationService implements FakeClient {
    @Override
    public String sendNotification(Notification notification) {

        log.debug("Sending fake response for sendNotification: QUEUED");
        if(notification.getChannel()==Channel.email){
            return "202";
        }
        return "PENDING";
    }

    @Override
    public String verifyOTP(String identifier, String token) {
        if(!token.equals("123456")){
            throw new BusinessException(ExceptionType.INVALID_OTP,"OTP is Invalid");
        }
        log.debug("Sending fake response for sendNotification: VERIFIED");
        return "VERIFIED";
    }
 
    @Override
    public String sendOTP(String identifier, Channel channel) {
        log.debug("Sending fake response for sendNotification: 12345");
        return "123456";
    }
}
