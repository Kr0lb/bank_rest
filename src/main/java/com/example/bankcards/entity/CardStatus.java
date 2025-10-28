package com.example.bankcards.entity;

import lombok.Getter;

@Getter
public enum CardStatus {

    ACTIVE("Активна"), BLOCKED("Заблокирована"), EXPIRED("Истек срок действия");

    private final String value;

    CardStatus(String value) {
        this.value = value;
    }
}
