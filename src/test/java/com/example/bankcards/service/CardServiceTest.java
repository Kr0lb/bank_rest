package com.example.bankcards.service;

import com.example.bankcards.dto.UserDto;
import com.example.bankcards.dto.request.CardFilterRequest;
import com.example.bankcards.dto.request.PageRequest;
import com.example.bankcards.dto.response.CardPageResponse;
import com.example.bankcards.dto.response.CardResponse;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.CardNotFoundException;
import com.example.bankcards.exception.CardStatusException;
import com.example.bankcards.exception.NotEnoughFundsException;
import com.example.bankcards.exception.UserNotFoundException;
import com.example.bankcards.mapper.CardMapper;
import com.example.bankcards.mapper.UserMapper;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CardServiceTest {

    @InjectMocks
    private CardService cardService;

    @Mock
    private CardRepository cardRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private CardMapper cardMapper;

    private User user;
    private UserDto userDto;
    private Card cardOne;
    private Card cardTwo;
    private CardResponse cardResponse;
    private CardFilterRequest cardFilterRequest;
    private PageRequest pageRequest;


    @BeforeEach
    void setUp() {
        user = User.builder().id(UUID.randomUUID()).email("ivanov@example.com").firstname("Ivan").build();
        userDto = new UserDto(user.getId(), user.getEmail());
        cardOne = Card.builder().id(UUID.randomUUID()).balance(BigDecimal.valueOf(100)).number("1234 5678 4321 8765")
                .user(user).build();
        cardTwo = Card.builder().id(UUID.randomUUID()).balance(BigDecimal.valueOf(3200)).number("8549 6751 4586 6548")
                .user(user).build();
        cardResponse = CardResponse.builder().id(cardOne.getId()).owner(userDto).build();
        cardFilterRequest = CardFilterRequest.builder().page(1).size(20).build();
        pageRequest = CardFilterRequest.builder().page(1).size(20).build();
    }

    @Test
    @DisplayName("Создание карты - успешно")
    public void testCreateCard_Success() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        cardService.create(user.getId());

        verify(userRepository).findById(user.getId());
        verify(cardRepository).save(any());
    }

    @Test
    @DisplayName("Создание карты - ошибка")
    public void testCreateCard_ThrowException() {
        assertThrows(UserNotFoundException.class, () -> cardService.create(user.getId()));
    }

    @Test
    @DisplayName("Получение списка карт пользователем")
    public void testGetCardByUserId_Success() {
        Page<Card> cardPage = new PageImpl<>(List.of(cardOne));

        when(cardRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(cardPage);

        when(userMapper.toUserDto(user)).thenReturn(userDto);
        when(cardMapper.toCardResponse(cardOne, userDto)).thenReturn(cardResponse);

        CardPageResponse result = cardService.getCardByUserId(user.getId(), cardFilterRequest);

        assertNotNull(result);
        assertEquals(1, result.getPage());

        verify(cardRepository).findAll(any(Specification.class), any(Pageable.class));
        verify(cardMapper).toCardResponse(cardOne, userDto);
        verify(userMapper).toUserDto(user);
    }

    @Test
    @DisplayName("Удаление карты")
    public void testDeleteCard() {
        cardService.delete(cardOne.getId());

        verify(cardRepository).deleteById(cardOne.getId());
    }

    @Test
    @DisplayName("Получение всех карт")
    public void testGetCardAll_Success() {
        Page<Card> cardPage = new PageImpl<>(List.of(cardOne));

        when(cardRepository.findAll(any(Pageable.class)))
                .thenReturn(cardPage);

        when(userMapper.toUserDto(user)).thenReturn(userDto);
        when(cardMapper.toCardResponse(cardOne, userDto)).thenReturn(cardResponse);

        CardPageResponse result = cardService.getAll(pageRequest);

        assertNotNull(result);
        assertEquals(1, result.getPage());

        verify(cardRepository).findAll(any(Pageable.class));
        verify(cardMapper).toCardResponse(cardOne, userDto);
        verify(userMapper).toUserDto(user);
    }

    @Test
    @DisplayName("Получение баланса")
    public void testGetBalance_Success() {
        when(cardRepository.findByCardIdAndUserId(cardOne.getId(), user.getId()))
                .thenReturn(Optional.of(cardOne));

        cardService.getBalance(user.getId(), cardOne.getId());

        verify(cardRepository).findByCardIdAndUserId(cardOne.getId(), user.getId());
    }

    @Test
    @DisplayName("Успешный перевод средств")
    public void testTransfer_Success() {
        when(cardRepository.findByCardIdAndUserId(cardOne.getId(), user.getId())).thenReturn(Optional.of(cardOne));
        when(cardRepository.findByCardIdAndUserId(cardTwo.getId(), user.getId())).thenReturn(Optional.of(cardTwo));

        cardService.transfer(cardOne.getId(), cardTwo.getId(), user.getId(), new BigDecimal(100));

        verify(cardRepository).findByCardIdAndUserId(cardOne.getId(), user.getId());
        verify(cardRepository).findByCardIdAndUserId(cardTwo.getId(), user.getId());
    }

    @Test
    @DisplayName("Перевод средств с ошибкой")
    public void testTransfer_ThrowException() {
        when(cardRepository.findByCardIdAndUserId(cardOne.getId(), user.getId())).thenReturn(Optional.of(cardOne));

        assertThrows(NotEnoughFundsException.class, () -> cardService
                .transfer(cardOne.getId(), cardTwo.getId(), user.getId(), new BigDecimal(300)));

        verify(cardRepository).findByCardIdAndUserId(cardOne.getId(), user.getId());
    }


    @Test
    @DisplayName("Успешное изменение статуса")
    void changeStatus_Success() {
        when(cardRepository.findById(cardOne.getId())).thenReturn(Optional.of(cardOne));

        cardService.changeStatus(cardOne.getId(), CardStatus.BLOCKED);

        verify(cardRepository).findById(cardOne.getId());
        verify(cardRepository).save(cardOne);
    }

    @Test
    @DisplayName("Изменение статуса с ошибкой")
    void changeStatus_ThrowException() {
        assertThrows(CardNotFoundException.class, () -> cardService.changeStatus(cardOne.getId(), CardStatus.BLOCKED));
    }

    @Test
    @DisplayName("Успешная блокировка карты")
    void blockCard() {
        when(cardRepository.findByCardIdAndUserId(cardOne.getId(), user.getId())).thenReturn(Optional.of(cardOne));

        cardService.blockCard(cardOne.getId(), user.getId());

        verify(cardRepository).findByCardIdAndUserId(cardOne.getId(), user.getId());
    }

    @Test
    @DisplayName("Блокировка карты с ошибкой 'Карта не найдена'")
    void blockCard_ThrowException_CardNotFound() {
        assertThrows(CardNotFoundException.class, () -> cardService.blockCard(cardOne.getId(), user.getId()));
    }

    @Test
    @DisplayName("Изменение статуса с ошибкой 'Неверный статус'")
    void blockCard_ThrowException_CardStatus_Blocked() {
        when(cardRepository.findByCardIdAndUserId(cardOne.getId(), user.getId())).thenReturn(Optional.of(cardOne));
        cardOne.setStatus(CardStatus.BLOCKED);
        assertThrows(CardStatusException.class, () -> cardService.blockCard(cardOne.getId(), user.getId()));
    }

    @Test
    @DisplayName("Изменение статуса с ошибкой 'Неверный статус'")
    void blockCard_ThrowException_CardStatus_Expired() {
        when(cardRepository.findByCardIdAndUserId(cardOne.getId(), user.getId())).thenReturn(Optional.of(cardOne));
        cardOne.setStatus(CardStatus.EXPIRED);
        assertThrows(CardStatusException.class, () -> cardService.blockCard(cardOne.getId(), user.getId()));
    }

}
