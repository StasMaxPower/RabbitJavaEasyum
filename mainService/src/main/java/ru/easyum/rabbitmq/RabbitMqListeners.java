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
        if (client.getVerificationStatus().name().equals("REJECTED")) {
            clientService.remove(client);
            log.info("Клиент с именем {} и статусом {} удален", client.getName(), client.getVerificationStatus());
        } else {
            clientService.save(client);
            log.info("Клиент с именем {} и статусом {} сохранен", client.getName(), client.getVerificationStatus());
        }
    }
}
