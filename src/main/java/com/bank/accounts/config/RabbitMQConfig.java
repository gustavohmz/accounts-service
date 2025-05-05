package com.bank.accounts.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue accountCreatedQueue() {
        return new Queue("account.created.queue", false);
    }

    @Bean
    public TopicExchange accountExchange() {
        return new TopicExchange("account.exchange");
    }

    @Bean
    public Binding binding(Queue accountCreatedQueue, TopicExchange accountExchange) {
        return BindingBuilder.bind(accountCreatedQueue).to(accountExchange).with("account.created");
    }
}
