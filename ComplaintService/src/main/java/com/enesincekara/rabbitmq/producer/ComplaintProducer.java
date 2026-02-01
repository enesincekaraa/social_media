package com.enesincekara.rabbitmq.producer;

import com.enesincekara.constants.RabbitConstants;
import com.enesincekara.model.ComplaintCreateModel;
import com.enesincekara.model.NotificationModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ComplaintProducer {
    private final RabbitTemplate rabbitTemplate;

    public void sendCreateComplaintMessage(ComplaintCreateModel model) {
        rabbitTemplate.convertAndSend(
                RabbitConstants.EXCHANGE_COMPLAINT,
                RabbitConstants.ROUTING_KEY_CREATE_COMPLAINT,
                model
        );
        System.out.println("Send complaint message to queue" + model.title());
    }

    public void sendUpdateComplaintMessage(NotificationModel model) {
        rabbitTemplate.convertAndSend(
                RabbitConstants.EXCHANGE_COMPLAINT,
                RabbitConstants.ROUTING_KEY_NOTIFICATION,
                model
        );
    }
}
