package com.enesincekara.rabbitmq.consumer;

import com.enesincekara.entity.User;
import com.enesincekara.rabbitmq.config.RabbitMqConfig;
import com.enesincekara.rabbitmq.model.RegisterModel;
import com.enesincekara.rabbitmq.model.SoftDeleteModel;
import com.enesincekara.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserConsumer {
    private final UserService  userService;

    @RabbitListener(queues = RabbitMqConfig.QUEUE_CREATE_PROFILE)
    public void receiveCreateProfileMessage(RegisterModel model) {
        System.out.println("Received CreateProfileMessage from UserConsumer: " + model.username());

        userService.createNewProfile(model);

    }
}
