package com.bank.accounts.event;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class AccountEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public AccountEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(AccountCreatedEvent event) {
        rabbitTemplate.convertAndSend("account.exchange", "account.created", event);
    }
}
