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
    public static final String QUEUE_CREATE_PROFILE="queue-create-profile";
    public static final String QUEUE_UPDATE_AUTH = "queue-update-auth";

    public static final String ROUTING_KEY_CREATE_PROFILE="routing-key-create-profile";


    public static final String EXCHANGE_AUTH="exchange-auth";


    @Bean
    public DirectExchange authExchange() {
        return new DirectExchange(EXCHANGE_AUTH);
    }
    @Bean
    public Queue authQueue() {
        return new Queue(QUEUE_CREATE_PROFILE);
    }
    @Bean
    public Binding bindingCreateProfile(final Queue createProfileQueue, final DirectExchange authExchange) {
        return BindingBuilder.bind(createProfileQueue).to(authExchange).with(ROUTING_KEY_CREATE_PROFILE);
    }


    //Json Converter
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();


    }
}
