package com.example.bankcards.service;

import com.example.bankcards.dto.request.CardFilterRequest;
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
import com.example.bankcards.repository.specifications.CardSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CardMapper cardMapper;

    public void changeStatus(UUID cardId, CardStatus status) {
        Card card = cardRepository.findById(cardId).orElseThrow(() ->
                new CardNotFoundException("Карта не найдена"));
        card.setStatus(status);
        cardRepository.save(card);
    }

    public void blockCard(UUID cardId, UUID userId) {
        Card card = checkCard(cardId, userId);
        card.setStatus(CardStatus.BLOCKED);
        cardRepository.save(card);
    }

    public void create(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("Пользователь не найден"));

        Card card = new Card();
        card.setUser(user);
        card.setBalance(BigDecimal.ZERO);
        card.setStatus(CardStatus.ACTIVE);
        card.setNumber(createNumber());

        cardRepository.save(card);
    }

    private String createNumber() {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        int i = 0;
        while (builder.length() < 19) {
            builder.append(random.nextInt(10));
            if (++i % 4 == 0 && builder.length() < 18)
                builder.append(" ");
        }

        return builder.toString();
    }

    public void delete(UUID cardId) {
        cardRepository.deleteById(cardId);
    }

    public CardPageResponse getAll(com.example.bankcards.dto.request.PageRequest pageRequest) {
        Page<Card> cardPage = cardRepository.findAll(PageRequest.of(pageRequest.getPage(), pageRequest.getSize()));

        List<CardResponse> cardResponses = cardPage.getContent().stream().map(card ->
                cardMapper.toCardResponse(card, userMapper.toUserDto(card.getUser()))).toList();

        return CardPageResponse.builder().cards(cardResponses)
                .page(cardPage.getNumber() + 1).totalCount(cardPage.getTotalPages()).build();
    }

    public CardPageResponse getCardByUserId(UUID userId, CardFilterRequest request) {
        Specification<Card> spec = Specification.where(CardSpecification.hasUserId(userId)
                .or(CardSpecification.hasStatus(request.getStatus()))
                .or(CardSpecification.likeNumber(request.getNumber())));

        Page<Card> cardPage = cardRepository.findAll(spec,
                PageRequest.of(request.getPage(), request.getSize()));

        List<CardResponse> cardResponses = cardPage.getContent().stream().map(card ->
                cardMapper.toCardResponse(card, userMapper.toUserDto(card.getUser()))).toList();

        return CardPageResponse.builder().cards(cardResponses)
                .page(cardPage.getNumber() + 1).totalCount(cardPage.getTotalPages()).build();
    }

    public BigDecimal getBalance(UUID userId, UUID cardId) {
        Card card = checkCard(cardId, userId);
        return card.getBalance();
    }

    @Transactional
    public void transfer(UUID fromCardId, UUID toCardId, UUID userId, BigDecimal price) {
        Card fromCard = checkCard(fromCardId, userId);

        if (fromCard.getBalance().compareTo(price) < 0)
            throw new NotEnoughFundsException("Недостаточно средств для перевода");

        Card toCard = checkCard(toCardId, userId);

        fromCard.setBalance(fromCard.getBalance().subtract(price));
        toCard.setBalance(toCard.getBalance().add(price));
    }

    private Card checkCard(UUID cardId, UUID userId) {
        Card card = cardRepository.findByCardIdAndUserId(cardId, userId).orElseThrow(() ->
                new CardNotFoundException("Неизвестная карта"));

        if (card.getStatus() == CardStatus.BLOCKED)
            throw new CardStatusException("Карта заблокирована");
        else if (card.getStatus() == CardStatus.EXPIRED)
            throw new CardStatusException("Срок действия карты истек");

        return card;
    }

}
