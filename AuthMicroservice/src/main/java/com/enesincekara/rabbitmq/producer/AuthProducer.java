package com.enesincekara.rabbitmq.producer;


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
                "exchange-auth",
                "routing-key-create-profile",
                model
        );
    }
}
