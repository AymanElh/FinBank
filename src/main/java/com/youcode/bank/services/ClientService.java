package com.youcode.bank.services;

import com.youcode.bank.model.Client;

import java.util.*;

public class ClientService {
    // Primary storage keyed by clientId for O(1) access and uniqueness
    private final Map<String, Client> clientsById = new HashMap<>();
    // Secondary index by email to support O(1) login lookup and enforce unique emails
    private final Map<String, Client> clientsByEmail = new HashMap<>();

    public Client addNewClient(Client client) {
        // If an existing client with same email exists, replace or keep minimal behavior: prevent duplicates
        // Minimal policy: last write wins (can be adjusted to throw if needed)
        clientsById.put(client.getUserId(), client);
        clientsByEmail.put(client.getEmail(), client);
        return client;
    }

    public List<Client> getAllClients() { return Collections.unmodifiableList(new ArrayList<>(clientsById.values())); }

    public Optional<Client> getClientById(String clientId) {
        return Optional.ofNullable(clientsById.get(clientId));
    }

    public Optional<Client> login(String email, String password) {
        Client c = clientsByEmail.get(email);
        if (c != null && c.getPassword().equals(password)) {
            return Optional.of(c);
        }
        return Optional.empty();
    }

}
