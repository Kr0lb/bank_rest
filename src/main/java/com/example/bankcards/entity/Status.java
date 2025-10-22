package com.example.bankcards.entity;

import lombok.Getter;

@Getter
public enum Status {
    ACTIVE("Активна"), BLOCKED("Заблокирована"), EXPIRED("Истек срок действия");

    private final String value;

    Status(String value) {
        this.value = value;
    }
}
