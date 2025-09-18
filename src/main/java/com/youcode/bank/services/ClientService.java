package com.youcode.bank.services;

import com.youcode.bank.model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientService {
    private ArrayList<Client> clients = new ArrayList<>();

    public Client addNewClient(Client client) {
        clients.add(client);
        return client;
    }

    public List<Client> getAllClients() { return clients; }

    public Optional<Client> getClientById(String clientId) {
        return clients
                .stream()
                .filter(client -> client.getUserId().equals(clientId))
                .findFirst();
    }

    public Optional<Client> login(String email, String password) {
        System.out.println(clients);
        return clients
                .stream()
                .filter(client -> client.getEmail().equals(email) && client.getPassword().equals(password))
                .findFirst();
    }

}
