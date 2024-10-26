package ru.easyum.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.easyum.domain.Client;


@Service
public class RabbitMqService {

    public static final String MAIN_EXCHANGE = "main-exchange";

    private final RabbitTemplate rabbitTemplate;

    public RabbitMqService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendClientApprovedEvent(Client client) {
        rabbitTemplate.convertAndSend("clients.approval_result", client);
    }
}
