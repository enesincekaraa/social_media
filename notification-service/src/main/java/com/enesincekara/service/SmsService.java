package com.enesincekara.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.phone-number}")
    private String fromNumber;

    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
    }

    public void sendSms(String toNumber, String messageBody) {
        try {
            Message.creator(
                    new com.twilio.type.PhoneNumber(toNumber), // Alıcı
                    new PhoneNumber(fromNumber), // Gönderen (Twilio numaran)
                    messageBody
            ).create();
            System.out.println("SMS başarıyla gönderildi: " + toNumber);
        } catch (Exception e) {
            System.err.println("SMS gönderim hatası: " + e.getMessage());
        }
    }
}
