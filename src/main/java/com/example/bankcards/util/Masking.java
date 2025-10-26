package com.example.bankcards.util;


public class Masking {

    public static String maskingCard(String cardNumber) {
        return cardNumber.replaceAll("\\s", "").replaceAll("[\\d ](?=\\d{4})", "*")
                .replaceAll("(.{4})(?!$)", "$1 ");
    }

}
