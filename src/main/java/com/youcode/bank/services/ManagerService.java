package com.youcode.bank.services;

import com.youcode.bank.model.Manager;

import java.util.ArrayList;
import java.util.Optional;

public class ManagerService {
    public ArrayList<Manager> managers = new ArrayList<>();

    public void initializeManagerList() {
        Manager manager = new Manager("ayman", "ayman@gmail.com", "123456");
        managers.add(manager);
    }

    public Manager addManager(Manager manager) {
        managers.add(manager);
        return manager;
    }

    public Optional<Manager> login(String email, String password) {
        return managers.stream()
                .filter(manager -> manager.getEmail().equals(email) && manager.getPassword().equals(password))
                .findFirst();
    }
}
