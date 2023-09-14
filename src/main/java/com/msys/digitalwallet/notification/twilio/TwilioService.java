package com.msys.digitalwallet.notification.twilio;


import com.msys.digitalwallet.notification.common.exception.BusinessException;
import com.msys.digitalwallet.notification.common.exception.enums.ExceptionType;
import com.msys.digitalwallet.notification.config.Twilio;
import com.msys.digitalwallet.notification.enums.Channel;
import com.msys.digitalwallet.notification.integration.TwilioClient;
import com.msys.digitalwallet.notification.model.Notification;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Slf4j
@Service
public class TwilioService implements TwilioClient {


    @Autowired
    Twilio twilio;

    public String sendOTP( String identifier, Channel channel ){

        intiateTwilioProcess();
        Verification verification;
        try{
            verification = Verification.creator(
                            decoder(twilio.getSid()),
                            identifier,
                            channel.name())
                    .create();
            log.debug("Twilio sendOTP Response Status: {}",verification.getStatus());
        }
        catch (Exception e){
            throw new BusinessException(ExceptionType.NOTIFICATION_ERROR,"Unable to send OTP from Twilio");
        }

        return verification.getStatus();
    }

    @Override
    public String sendNotification(Notification notification) {
        String response;
        if(notification.getChannel().equals(Channel.email)){
                response = sendNotificationEmail(notification);
        } else if(notification.getChannel().equals(Channel.whatsapp)){
            response = sendWhatsappNotification(notification.getIdentifier()
                    ,notification.getMessage());
        } else {
            response = sendNotificationSMS(notification.getIdentifier()
                    ,notification.getMessage());
        }
        return response;
    }

    public String verifyOTP(String identifier, String token) {

        intiateTwilioProcess();
        VerificationCheck verificationCheck;
        try{
            verificationCheck = VerificationCheck.creator(
                            decoder(twilio.getSid()))
                    .setTo(identifier)
                    .setCode(token)
                    .create();
            log.debug("Twilio verifyOTP Response Status: {}",verificationCheck.getStatus());
        }catch(Exception e){
            throw new BusinessException(ExceptionType.NOTIFICATION_ERROR
                    ,"Unable to verify OTP from Twilio");
        }
        return verificationCheck.getStatus();

    }
    public String sendNotificationSMS(String identifier, String message){

        intiateTwilioProcess();
        Message twilioMessage;
        try{
            twilioMessage = Message.creator(new PhoneNumber(identifier),
                    new PhoneNumber("+12765826739"), message).create();
            log.debug("Twilio sendNotificationSMS Response Status :{}"
                    ,twilioMessage.getStatus().name());

        }catch(Exception e){
            throw new BusinessException(ExceptionType.NOTIFICATION_ERROR
                    ,"Unable to send Notification from Twilio");
        }
        return twilioMessage.getStatus().name();
    }

    public String sendWhatsappNotification(String identifier, String message){

        intiateTwilioProcess();
        Message twilioMessage;
        try{
            twilioMessage = Message.creator(
                            new com.twilio.type.PhoneNumber("whatsapp:"+identifier),
                            new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                            message)
                    .create();
            log.debug("Twilio sendWhatsappNotification Response Status : {}"
                    ,twilioMessage.getStatus().name());
        }catch(Exception e){
            throw new BusinessException(ExceptionType.NOTIFICATION_ERROR
                    ,"Unable to whatsapp send Notification from Twilio");
        }
        return twilioMessage.getStatus().name();
    }

    public String sendNotificationEmail(Notification notification) {

        Email from = new Email(twilio.getSenderEmail());
        Email to = new Email(notification.getIdentifier());
        Content content = new Content("text/html", notification.getMessage());
        Mail mail = new Mail(from, notification.getSubject(), to, content);

        SendGrid sg = new SendGrid(decoder(twilio.getSendGridId()));
        Request request = new Request();
        Response response;
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        try {
            request.setBody(mail.build());
            response = sg.api(request);
            log.debug("Twilio sendNotificationEmail Response Status : {}"
                    ,response.getStatusCode());
            if(response.getStatusCode()==401){
                throw new BusinessException(ExceptionType.NOTIFICATION_ERROR, response.getBody());
            }
        } catch(BusinessException e){
            throw new BusinessException(e);
        } catch (Exception e) {
            throw new BusinessException(ExceptionType.NOTIFICATION_ERROR
                    ,"Unable to email send Notification from Twilio");
        }

        return String.valueOf(response.getStatusCode());
    }

    private void intiateTwilioProcess() {
        try{
            com.twilio.Twilio.init(decoder(twilio.accountSid),decoder(twilio.getAuthToken()));
        } catch (Exception e){
            throw new BusinessException(ExceptionType.NOTIFICATION_ERROR
                    ,"Unable to whatsapp send Notification from Twilio");
        }
    }

    private String decoder(String encodedString) {
        return new String(Base64.getDecoder().decode(encodedString));
    }

}
