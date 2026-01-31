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
    public DirectExchange authExchange() {
        return new DirectExchange(RabbitConstants.EXCHANGE_AUTH);
    }

    // 1. Önce Exchange'i tanımlıyoruz (Idempotent: Varsa dokunmaz, yoksa açar)
    @Bean
    public DirectExchange userExchange() {
        return new DirectExchange(RabbitConstants.EXCHANGE_USER);
    }

    @Bean
    public DirectExchange complaintExchange() {
        return new DirectExchange(RabbitConstants.EXCHANGE_COMPLAINT);
    }



    // 2. Şifre güncelleme kuyruğunu tanımlıyoruz
    @Bean
    public Queue updatePasswordQueue() {
        // durable: true -> RabbitMQ kapansa bile mesajlar kaybolmaz
        return new Queue(RabbitConstants.QUEUE_UPDATE_PASSWORD, true);
    }

    // 3. Kuyruğu Exchange'e Routing Key ile mühürlüyoruz (Binding)
    @Bean
    public Binding bindingUpdatePassword(Queue updatePasswordQueue, DirectExchange userExchange) {
        return BindingBuilder.bind(updatePasswordQueue)
                .to(userExchange)
                .with(RabbitConstants.ROUTING_KEY_UPDATE_PASSWORD);
    }


    @Bean
    public Queue authSoftDeleteQueue(){
        return new Queue(RabbitConstants.QUEUE_AUTH_SOFT_DELETE, true);
    }

    @Bean
    public Binding bindingAuthSoftDelete(final Queue authSoftDeleteQueue,final DirectExchange userExchange) {

        return BindingBuilder.bind(authSoftDeleteQueue)
                .to(userExchange)
                .with(RabbitConstants.ROUTING_KEY_AUTH_SOFT_DELETE);
    }

    @Bean
    public Queue updateAuthQueue() {
        return new Queue(RabbitConstants.QUEUE_UPDATE_AUTH, true);
    }

    @Bean
    public Binding bindingUpdateAuth(final Queue updateAuthQueue,final DirectExchange userExchange) {
        return BindingBuilder.bind(updateAuthQueue)
                .to(userExchange)
                .with(RabbitConstants.ROUTING_KEY_UPDATE_AUTH);
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
    public Queue createProfileQueue() { // Metod ismini 'createProfileQueue' yaptık
        return new Queue(RabbitConstants.QUEUE_CREATE_PROFILE);
    }


    @Bean
    public Binding bindingCreateProfile(final Queue createProfileQueue, final DirectExchange authExchange) {
        return BindingBuilder.bind(createProfileQueue).to(authExchange).with(RabbitConstants.ROUTING_KEY_CREATE_PROFILE);
    }


    @Bean
    public Queue loginQueueEvent() {
        return new Queue(RabbitConstants.QUEUE_LOGIN_EVENT);
    }
    @Bean
    public Binding bindingLoginEvent(final Queue loginQueueEvent, final DirectExchange authExchange) {
        return BindingBuilder.bind(loginQueueEvent).to(authExchange).with(RabbitConstants.ROUTING_KEY_LOGIN_EVENT);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}