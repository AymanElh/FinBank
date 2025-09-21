package com.youcode.bank.services;

import com.youcode.bank.model.Manager;

import java.util.*;

public class ManagerService {
    private final Map<String, Manager> managersByEmail = new HashMap<>();

    public void initializeManagerList() {
        Manager manager = new Manager("ayman", "ayman@gmail.com", "123456");
        managersByEmail.put(manager.getEmail(), manager);
    }

    public Manager addManager(Manager manager) {
        managersByEmail.put(manager.getEmail(), manager);
        return manager;
    }

    public Optional<Manager> login(String email, String password) {
        Manager m = managersByEmail.get(email);
        if (m != null && m.getPassword().equals(password)) {
            return Optional.of(m);
        }
        return Optional.empty();
    }

    public List<Manager> getAllManagers() { return Collections.unmodifiableList(new ArrayList<>(managersByEmail.values())); }
}
