package com.enesincekara.rabbitmq.consumer;

import com.enesincekara.client.UserClient;
import com.enesincekara.constants.RabbitConstants;
import com.enesincekara.model.NotificationModel;
import com.enesincekara.model.UserContactResponse;
import com.enesincekara.service.EmailService;
import com.enesincekara.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationConsumer {
    private final UserClient client;
    private final EmailService emailService;
    private final SmsService smsService;

    @RabbitListener(queues = RabbitConstants.QUEUE_NOTIFICATION)
    public void receiveNotification(NotificationModel model) {

        System.out.println("Received Notification from UserConsumer: " + model.authId());

        UserContactResponse contact = client.getContactInfo(model.authId());

        String messageContent = "Eynesil Bel: " +model.message();

        String subject = "Şikayetiniz Hakkında Bilgilendirme";
        String body = "Sayın Vatandaş," +
                "\n\n" +
                model.message() +
                "\n\n" +
                "Eynesil Belediyesi Şeffaf Yönetim Sistemi.";

        emailService.sendEmail(contact.email(), subject, body);
        smsService.sendSms(contact.phoneNumber(), messageContent);

        System.out.println("++++++++++ BİLDİRİM AKIŞI ++++++++++");
        System.out.println("E-POSTA: " + contact.email());
        System.out.println("TELEFON: " + contact.phoneNumber());
        System.out.println("Mesaj: " + model.message());
    }





}
