package ru.easyum.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.easyum.domain.Client;

@Service
@Slf4j
public class NamedService {

    private final RabbitTemplate rabbitTemplate;

    public NamedService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void setNameAndSendNext(Client client) {
        client.setName("JavaClient_" + client.getId());
        log.info("������� ����������� ��� {}", client.getName());
        rabbitTemplate.convertAndSend("clients.after.named", client);
    }

    public void getApprovalAndSendToMain(Client client) {
        log.info("������� ����������� ��� {} � ������ {}", client.getName(), client.getVerificationStatus());
        rabbitTemplate.convertAndSend("clients.approvalNamed_result", client);
    }
}
