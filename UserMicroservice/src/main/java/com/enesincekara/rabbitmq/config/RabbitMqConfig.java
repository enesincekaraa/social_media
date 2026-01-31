package com.enesincekara.rabbitmq.config;

import com.enesincekara.constants.RabbitConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMqConfig {

    // 1. Santral (Exchange) İsmi: Mesajlar önce buraya gelir.
    public static final String EXCHANGE_USER = "exchange-user";

    // 2. Adres (Routing Key): Mesajın hangi kuyruğa gideceğini belirleyen etiket.
    public static final String ROUTING_KEY_AUTH_SOFT_DELETE = "routing-key-auth-soft-delete";
    public static final String ROUTING_KEY_UPDATE_AUTH = "routing-key-update-auth";
    public static final String ROUTING_KEY_UPDATE_PASSWORD = "routing-key-update-password";

    // 3. Kutu (Queue) İsmi: Mesajların alıcıyı beklediği yer.
    public static final String QUEUE_AUTH_SOFT_DELETE = "queue-auth-soft-delete";
    public static final String QUEUE_CREATE_PROFILE = "queue-create-profile";
    public static final String QUEUE_UPDATE_AUTH = "queue-update-auth";
    public static final String QUEUE_LOGIN_EVENT = "queue-login-event";
    public static final String QUEUE_UPDATE_PASSWORD = "queue-update-password";

    @Bean
    public DirectExchange userExchange() {
        return new DirectExchange(EXCHANGE_USER);
    }

    @Bean
    public DirectExchange authExchange() {
        return new DirectExchange(RabbitConstants.EXCHANGE_AUTH);
    }

    @Bean
    public Queue createProfileQueue() {
        return new Queue(RabbitConstants.QUEUE_CREATE_PROFILE);
    }

    @Bean
    public Binding bindingCreateProfile(Queue createProfileQueue, DirectExchange authExchange) {
        return BindingBuilder.bind(createProfileQueue)
                .to(authExchange)
                .with(RabbitConstants.ROUTING_KEY_CREATE_PROFILE);
    }

    @Bean
    public Queue loginQueueEvent() {
        return new Queue(RabbitConstants.QUEUE_LOGIN_EVENT);
    }
    @Bean
    public Binding bindingLoginEvent(Queue loginQueueEvent, DirectExchange authExchange) {
        return BindingBuilder.bind(loginQueueEvent)
                .to(authExchange)
                .with(RabbitConstants.ROUTING_KEY_LOGIN_EVENT);
    }


    @Bean
    public Queue authSoftDeleteQueue() {
        return new Queue(QUEUE_AUTH_SOFT_DELETE);
    }

    @Bean
    public Queue updateAuthQueue() {
        return new Queue(QUEUE_UPDATE_AUTH);
    }

    @Bean
    public Queue updatePasswordQueue() {
        return new Queue(QUEUE_UPDATE_PASSWORD);
    }


    @Bean
    public Binding bindingAuthSoftDelete(final Queue authSoftDeleteQueue, final DirectExchange userExchange) {
        return BindingBuilder.bind(authSoftDeleteQueue).to(userExchange).with(ROUTING_KEY_AUTH_SOFT_DELETE);
    }

    @Bean
    public Binding bindingUpdateAuth(final Queue updateAuthQueue, final DirectExchange userExchange) {
        return BindingBuilder.bind(updateAuthQueue).to(userExchange).with(ROUTING_KEY_UPDATE_AUTH);
    }

    @Bean
    public Binding bindingUpdatePassword(final Queue updatePasswordQueue, final DirectExchange userExchange) {
        return BindingBuilder.bind(updatePasswordQueue).to(userExchange).with(ROUTING_KEY_UPDATE_PASSWORD);
    }


    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
