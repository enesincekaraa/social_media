package com.enesincekara.rabbitmq.consumer;

import com.enesincekara.client.UserClient;
import com.enesincekara.constants.RabbitConstants;
import com.enesincekara.model.NotificationModel;
import com.enesincekara.model.UserContactResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationConsumer {
    private final UserClient client;

    @RabbitListener(queues = RabbitConstants.QUEUE_NOTIFICATION)
    public void receiveNotification(NotificationModel model) {

        System.out.println("Received Notification from UserConsumer: " + model.authId());


        UserContactResponse contact = client.getContactInfo(model.authId());

        System.out.println("++++++++++ BİLDİRİM AKIŞI ++++++++++");
        System.out.println("E-POSTA: " + contact.email());
        System.out.println("TELEFON: " + contact.phoneNumber());
        System.out.println("Mesaj: " + model.message());
    }
}
