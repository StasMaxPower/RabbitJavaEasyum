package ru.easyum.services;

import ru.easyum.domain.Client;

import java.util.List;

public interface ClientService {
    List<Client> findAll();
    Client save(Client client);
    Client saveAndSendForApprove(Client client);
    Client saveAndSendForApproveAndWait(Client client);
}
