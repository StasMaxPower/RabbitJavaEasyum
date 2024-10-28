package ru.easyum.rabbitmq;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.easyum.domain.Client;
import ru.easyum.service.NamedService;


@Slf4j
@Component
public class RabbitMqListeners {

    private final NamedService namedService;

    public RabbitMqListeners(NamedService namedService) {
        this.namedService = namedService;
    }

    @RabbitListener(queues = "named-queue")
    public void newClientsEventsQueueListener(Client client) {
        namedService.setNameAndSendNext(client);
    }

    @RabbitListener(queues = "approval-named-queue")
    public void afterApprovalEventsQueueListener(Client client) {
        namedService.getApprovalAndSendToMain(client);
    }
}
