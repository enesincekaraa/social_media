package com.enesincekara.rabbitmq.consumer;

import com.enesincekara.constants.RabbitConstants;
import com.enesincekara.rabbitmq.model.PasswordChangeModel;
import com.enesincekara.rabbitmq.model.SoftDeleteModel;
import com.enesincekara.rabbitmq.config.RabbitMqConfig;
import com.enesincekara.rabbitmq.model.UserUpdateModel;
import com.enesincekara.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthConsumer {
    private final AuthService authService;

    @RabbitListener(queues = RabbitConstants.QUEUE_AUTH_SOFT_DELETE)
    public void receiveSoftDeleteMessage(SoftDeleteModel model){
        System.out.println("Received soft delete message! Deleting id:" + model.id());

        authService.softDelete(model.id());
    }

    @RabbitListener(queues = RabbitConstants.QUEUE_UPDATE_AUTH)
    public void receiveUpdateAuthMessage(UserUpdateModel model){
        System.out.println("Received update auth message!" + model.username());

        authService.updateAuth(model.authId(),model.username(),model.email(),model.phone());
    }

    @RabbitListener(queues = RabbitConstants.QUEUE_UPDATE_PASSWORD)
    public void receiveUpdatePasswordMessage(PasswordChangeModel model){
        System.out.println("Received update password message! " + model.authId());
        authService.updatePassword(model.authId(),model.newPassword());


    }
}
