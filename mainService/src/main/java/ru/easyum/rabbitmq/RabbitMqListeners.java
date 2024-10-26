package ru.easyum.rabbitmq;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.easyum.domain.Client;
import ru.easyum.services.ClientService;


@Slf4j
@Component
public class RabbitMqListeners {

    private final ClientService clientService;

    public RabbitMqListeners(ClientService clientService) {
        this.clientService = clientService;
    }

    @RabbitListener(queues = "approval-results-queue")
    public void newClientsEventsQueueListener(Client client) {
        clientService.save(client);
    }
}
