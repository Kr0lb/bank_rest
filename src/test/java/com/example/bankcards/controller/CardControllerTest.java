package com.example.bankcards.controller;

import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.exception.handler.GlobalExceptionHandler;
import com.example.bankcards.service.CardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static com.example.bankcards.TestConfig.setUpContext;
import static com.example.bankcards.TestConstants.CARDS_BASE_URL;
import static com.example.bankcards.TestConstants.DELETE_CARD_URL;
import static com.example.bankcards.TestConstants.GET_BALANCE_URL;
import static com.example.bankcards.TestConstants.GET_FILTER_CARDS_URL;
import static com.example.bankcards.TestConstants.PATCH_BLOCKED_CARD_URL;
import static com.example.bankcards.TestConstants.PATCH_STATUS_URL;
import static com.example.bankcards.TestConstants.POST_TRANSFER_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CardControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CardService cardService;

    private final UUID CARD_ID = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new CardController(cardService))
                .setControllerAdvice(GlobalExceptionHandler.class)
                .build();
    }

    @Test
    @DisplayName("Успешное изменение статуса")
    void changeStatus_200() throws Exception {
        mockMvc.perform(patch(PATCH_STATUS_URL.formatted(CARD_ID))
                        .param("status", CardStatus.EXPIRED.toString()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Изменение статуса с ошибкой")
    void changeStatus_400() throws Exception {
        mockMvc.perform(patch(PATCH_STATUS_URL.formatted(CARD_ID))
                        .param("status", "ACTIVE"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(get(CARDS_BASE_URL)).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Успешное удаление карты")
    void deleteCardById() throws Exception {
        mockMvc.perform(delete(DELETE_CARD_URL.formatted(CARD_ID))).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Изменение статуса с ошибкой")
    void getByUserWithFilter() throws Exception {
        setUpContext(UUID.randomUUID(), "ADMIN", true);

        mockMvc.perform(get(GET_FILTER_CARDS_URL)
                        .param("status", CardStatus.ACTIVE.toString()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Успешное получение баланса")
    void getBalance() throws Exception {
        setUpContext(UUID.randomUUID(), "ADMIN", true);

        mockMvc.perform(get(GET_BALANCE_URL.formatted(CARD_ID)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Успешный перевод средств")
    void transfer() throws Exception {
        setUpContext(UUID.randomUUID(), "ADMIN", true);

        mockMvc.perform(post(POST_TRANSFER_URL)
                        .param("cardFrom", CARD_ID.toString())
                        .param("cardTo", UUID.randomUUID().toString())
                        .param("price", "100.00"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Успешная блокировка")
    void userBlockedCard() throws Exception {
        setUpContext(UUID.randomUUID(), "ADMIN", true);

        mockMvc.perform(patch(PATCH_BLOCKED_CARD_URL.formatted(CARD_ID)))
                .andDo(print())
                .andExpect(status().isOk());
    }

}