package com.example.bankcards;

public final class TestConstants {

    public static final String CARDS_BASE_URL = "/api/v1/cards";
    public static final String DELETE_CARD_URL = CARDS_BASE_URL + "/%s";
    public static final String PATCH_STATUS_URL = CARDS_BASE_URL + "/status/%s";
    public static final String GET_BALANCE_URL = CARDS_BASE_URL + "/%s/balance";
    public static final String POST_TRANSFER_URL = CARDS_BASE_URL + "/transfer";
    public static final String GET_FILTER_CARDS_URL = CARDS_BASE_URL + "/filter";
    public static final String PATCH_BLOCKED_CARD_URL = CARDS_BASE_URL + "/%s/blocked";

    public static final String USERS_BASE_URL = "/api/v1/users";
    public static final String PATCH_USERS_BASE_URL = USERS_BASE_URL + "/%s";
    public static final String POST_CREATE_CARD_URL = USERS_BASE_URL + "/%s/cards";


    private TestConstants() {
    }

}
