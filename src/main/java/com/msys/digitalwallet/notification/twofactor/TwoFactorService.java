package com.msys.digitalwallet.notification.twofactor;

import com.msys.digitalwallet.notification.common.exception.BusinessException;
import com.msys.digitalwallet.notification.common.exception.enums.ExceptionType;
import com.msys.digitalwallet.notification.enums.Channel;
import com.msys.digitalwallet.notification.integration.TwoFactorClient;
import com.msys.digitalwallet.notification.model.Notification;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Slf4j
@Service
public class TwoFactorService  implements TwoFactorClient {
    @Override
    public String sendNotification(Notification notification) {
        if(notification.getChannel() == Channel.email || notification.getChannel() == Channel.whatsapp){

        }
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "module=TRANS_SMS&apikey=6ed5ae4e-c6f8-11ed-81b6-0200cd936042&to=91XXXXXXXXXX,91YYYYYYYYYY&from=HEADER&msg=DLT Approved Message Text Goes Here");
        Request request = new Request.Builder()
                .url("https://2factor.in/API/R1/")
                .method("POST", body)
                .build();
        Response response;
        try {
            response = client.newCall(request).execute();
            log.debug("Response Status : {}",response.isSuccessful());
        } catch (IOException e) {
            throw new BusinessException(ExceptionType.NOTIFICATION_ERROR,"Unable to verify OTP from 2Factor");
        }
        return response.message();
    }

    @Override
    public String verifyOTP(String identifier, String token) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://2factor.in/API/V1/6ed5ae4e-c6f8-11ed-81b6-0200cd936042/SMS/VERIFY3/+919790794687/"+token)
                .method("GET",null)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            log.debug("Response Status : {}",response.isSuccessful());
        } catch (IOException e) {

            throw new BusinessException(ExceptionType.NOTIFICATION_ERROR,"Unable to verify OTP from 2Factor");
        }
        return response.message();
    }

    @Override
    public String sendOTP(String identifier, Channel channel) {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url("https://2factor.in/API/V1/6ed5ae4e-c6f8-11ed-81b6-0200cd936042/SMS/+919790794687/AUTOGEN/sample")
                .method("GET",null)
                .build();
        Response response;
        try {
            response = client.newCall(request).execute();
            log.debug("Response Status : {}",response.isSuccessful());
        } catch (IOException e) {
            throw new BusinessException(ExceptionType.NOTIFICATION_ERROR,"Unable to verify OTP from 2Factor");
        }
        return response.message();
    }
}
