package com.enesincekara.rabbitmq.producer;

import com.enesincekara.constants.RabbitConstants;
import com.enesincekara.rabbitmq.model.PasswordChangeModel;
import com.enesincekara.rabbitmq.model.SoftDeleteModel;
import com.enesincekara.rabbitmq.model.UpdateUserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProducer {
    private final RabbitTemplate rabbitTemplate;

    public void sendSoftDelete(SoftDeleteModel model) {

        rabbitTemplate.convertAndSend(
                RabbitConstants.EXCHANGE_USER,
                RabbitConstants.ROUTING_KEY_AUTH_SOFT_DELETE,
                model
        );
    }

    public void sendUpdatedUser(UpdateUserModel model){

        rabbitTemplate.convertAndSend(
        RabbitConstants.EXCHANGE_USER,
        RabbitConstants.ROUTING_KEY_UPDATE_AUTH,
        model
        );
    }

    public void sendPasswordChange(PasswordChangeModel model) {
        rabbitTemplate.convertAndSend(
                RabbitConstants.EXCHANGE_USER,
                RabbitConstants.ROUTING_KEY_UPDATE_PASSWORD,
                model
        );
    }


}
