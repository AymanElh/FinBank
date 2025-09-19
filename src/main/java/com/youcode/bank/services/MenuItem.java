package com.youcode.bank.services;

public class MenuItem {
    private String text;
    private int value;

    public MenuItem(int value, String text) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public int getValue() {
        return value;
    }
}
