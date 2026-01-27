package com.enesincekara.rabbitmq.producer;

import com.enesincekara.rabbitmq.model.SoftDeleteModel;
import com.enesincekara.rabbitmq.config.RabbitMqConfig;
import com.enesincekara.rabbitmq.model.UpdateUserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProducer {
    private final RabbitTemplate rabbitTemplate;

    public void sendSoftDelete(SoftDeleteModel model) {
        // "Git şu santrale, şu adresi kullanarak şu paketi bırak" diyoruz.

        rabbitTemplate.convertAndSend(
                RabbitMqConfig.EXCHANGE_USER,
                RabbitMqConfig.ROUTING_KEY_AUTH_SOFT_DELETE,
                model
        );
    }

    public void sendUpdatedUser(UpdateUserModel model){

        rabbitTemplate.convertAndSend(
        RabbitMqConfig.EXCHANGE_USER,
        RabbitMqConfig.ROUTING_KEY_UPDATE_AUTH,
        model
        );
    }
}
