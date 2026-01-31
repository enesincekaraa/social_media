package com.enesincekara.rabbitmq.consumer;

import com.enesincekara.constants.RabbitConstants;
import com.enesincekara.model.ComplaintCreateModel;
import com.enesincekara.model.RegisterModel;
import com.enesincekara.rabbitmq.model.LoginModel;
import com.enesincekara.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserConsumer {
    private final UserService  userService;

    @RabbitListener(queues = RabbitConstants.QUEUE_CREATE_PROFILE)
    public void receiveCreateProfileMessage(RegisterModel model) {
        System.out.println("Received CreateProfileMessage from UserConsumer: " + model.username());
        userService.createNewProfile(model);
    }

    @RabbitListener(queues = RabbitConstants.QUEUE_LOGIN_EVENT)
    public void receiveLoginEvent(LoginModel model) {
        System.out.println("Received LoginEvent from UserConsumer: " + model.authId());

        userService.updateLastLogin(
                model.authId(),
                model.loginDate(),
                model.ipAddress()
        );

    }

    @RabbitListener(queues = RabbitConstants.QUEUE_CREATE_COMPLAINT)
    public void receiveCreateComplaintMessage(ComplaintCreateModel model) {
        System.out.println("Received CreateComplaintMessage from UserConsumer: " + model.authId());

        userService.increaseUserComplaintCount(model.authId());
    }


}
