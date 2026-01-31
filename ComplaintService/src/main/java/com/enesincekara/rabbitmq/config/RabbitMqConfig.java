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

    @Bean
    public DirectExchange complaintExchange() {
        return new DirectExchange(RabbitConstants.EXCHANGE_COMPLAINT);
    }
    @Bean
    public Queue createComplaintQueue() {
        return new Queue(RabbitConstants.QUEUE_CREATE_COMPLAINT, true);
    }
    @Bean
    public Binding bindingCreateComplaint(final Queue createComplaintQueue, final DirectExchange complaintExchange) {
        return BindingBuilder.bind(createComplaintQueue).to(complaintExchange).with(RabbitConstants.ROUTING_KEY_CREATE_COMPLAINT);
    }


    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
