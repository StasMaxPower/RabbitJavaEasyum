package ru.easyum.rabbitmq;


import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import ru.easyum.domain.Client;
import ru.easyum.domain.VerificationStatus;
import ru.easyum.services.RabbitMqService;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RabbitMqListeners {

    private final RabbitMqService rabbitMqService;

    public RabbitMqListeners(RabbitMqService rabbitMqService) {
        this.rabbitMqService = rabbitMqService;
    }

    @RabbitListener(queues = "new-clients-queue", ackMode = "MANUAL")
    public void newClientsEventsQueueListener(Client client,
                                              Channel channel,
                                              @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        // TimeUnit.SECONDS.sleep(10);
        channel.basicNack(tag, false, false);

        // Для ackMode = "AUTO" и перехода в dead letter exchange
        //throw new AmqpRejectAndDontRequeueException("Ooops");
    }

    @RabbitListener(queues = "all-clients-events-queue")
    public void allClientsEventsQueueListener(Message message) {
        log.info("В all-clients-events-queue было получено сообщение: {}", message);
    }

    @RabbitListener(queues = "dead-letter-queue")
    public void deadLetterQueueListener(Client client) {
        // log.info("Было получено dead-сообщение: {}", message);
        client.setVerificationStatus(getRandomStatus());
        rabbitMqService.sendClientApprovedEvent(client);

    }

    private VerificationStatus getRandomStatus() {
        Random random = new Random();
        return random.nextBoolean() ? VerificationStatus.VERIFIED : VerificationStatus.REJECTED;
    }

    @RabbitListener(queues = "new-clients-rpc-queue")
    public Client newClientsEventsRpcQueueListener(Client client) throws Exception {
        client.setVerificationStatus(getRandomStatus());
        TimeUnit.SECONDS.sleep(10);
        return client;
    }
}
