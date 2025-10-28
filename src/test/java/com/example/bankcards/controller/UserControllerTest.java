package com.example.bankcards.controller;

import com.example.bankcards.exception.handler.GlobalExceptionHandler;
import com.example.bankcards.service.CardService;
import com.example.bankcards.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static com.example.bankcards.TestConstants.PATCH_USERS_BASE_URL;
import static com.example.bankcards.TestConstants.POST_CREATE_CARD_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private CardService cardService;

    private final UUID USER_ID = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userService, cardService))
                .setControllerAdvice(GlobalExceptionHandler.class)
                .build();
    }

    @Test
    @DisplayName("Успешное создание карты")
    void createCard() throws Exception {
        mockMvc.perform(post(POST_CREATE_CARD_URL.formatted(USER_ID)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Успешное обновление пользователя администратором")
    void updateUserByAdmin_200() throws Exception {
        String json = """
                {
                    "email": "ivan@example.com"
                }""";

        mockMvc.perform(patch(PATCH_USERS_BASE_URL.formatted(USER_ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Обновление пользователя администратором с ошибкой")
    void updateUserByAdmin_400() throws Exception {
        String json = "invalid json";

        mockMvc.perform(patch(PATCH_USERS_BASE_URL.formatted(USER_ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}