package com.enesincekara.rabbitmq.config;

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

    public static final String QUEUE_AUTH_SOFT_DELETE = "queue-auth-soft-delete";
    public static final String QUEUE_CREATE_PROFILE = "queue-create-profile";
    public static final String QUEUE_UPDATE_AUTH = "queue-update-auth";
    public static final String QUEUE_LOGIN_EVENT = "queue-login-event";

    public static final String ROUTING_KEY_CREATE_PROFILE = "routing-key-create-profile";
    public static final String ROUTING_KEY_LOGIN_EVENT = "routing-key-login-event";

    public static final String EXCHANGE_AUTH = "exchange-auth";

    @Bean
    public DirectExchange authExchange() {
        return new DirectExchange(EXCHANGE_AUTH);
    }

    // 1. Kuyruk: Profil oluşturma
    @Bean
    public Queue createProfileQueue() { // Metod ismini 'createProfileQueue' yaptık
        return new Queue(QUEUE_CREATE_PROFILE);
    }

    // 2. Kuyruk: Login olayı
    @Bean
    public Queue loginQueueEvent() {
        return new Queue(QUEUE_LOGIN_EVENT);
    }

    // Binding 1: Parametre ismi 'createProfileQueue', yukarıdaki metod ismiyle aynı!
    @Bean
    public Binding bindingCreateProfile(final Queue createProfileQueue, final DirectExchange authExchange) {
        return BindingBuilder.bind(createProfileQueue).to(authExchange).with(ROUTING_KEY_CREATE_PROFILE);
    }

    // Binding 2: Parametre ismi 'loginQueueEvent', yukarıdaki metod ismiyle aynı!
    @Bean
    public Binding bindingLoginEvent(final Queue loginQueueEvent, final DirectExchange authExchange) {
        return BindingBuilder.bind(loginQueueEvent).to(authExchange).with(ROUTING_KEY_LOGIN_EVENT);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}