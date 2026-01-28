package com.enesincekara.rabbitmq.producer;


import com.enesincekara.rabbitmq.config.RabbitMqConfig;
import com.enesincekara.rabbitmq.model.LoginModel;
import com.enesincekara.rabbitmq.model.RegisterModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthProducer {
    private final RabbitTemplate rabbitTemplate;

    public void sendCreateProfileMessage(RegisterModel model) {
        rabbitTemplate.convertAndSend(
                RabbitMqConfig.EXCHANGE_AUTH,
                RabbitMqConfig.ROUTING_KEY_CREATE_PROFILE,
                model
        );
    }

    public void sendLoginEvent(LoginModel model) {
        rabbitTemplate.convertAndSend(
                RabbitMqConfig.EXCHANGE_AUTH,
                RabbitMqConfig.ROUTING_KEY_LOGIN_EVENT,
                model
        );
        System.out.println("Send login event" +model.authId());
    }
}
