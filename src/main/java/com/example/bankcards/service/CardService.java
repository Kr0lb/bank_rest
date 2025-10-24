package com.example.bankcards.service;

import com.example.bankcards.dto.request.CardFilterRequest;
import com.example.bankcards.dto.response.CardPageResponse;
import com.example.bankcards.dto.response.CardResponse;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.Status;
import com.example.bankcards.entity.User;
import com.example.bankcards.mapper.CardMapper;
import com.example.bankcards.mapper.UserMapper;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.repository.specifications.CardSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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

    public void changeStatus(UUID cardId, Status status) {

    }

    public void create(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException("Пользователь не найден"));

        Card card = new Card();
        card.setUser(user);
        card.setBalance(BigDecimal.ZERO);
        card.setStatus(Status.ACTIVE);
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
                .page(cardPage.getNumber()).totalCount(cardPage.getTotalPages()).build();
    }

    public CardPageResponse getCardByUser(String userId, CardFilterRequest request) {
        Specification<Card> spec = Specification.where(CardSpecification.hasStatus(request.getStatus())
                .or(CardSpecification.likeNumber(request.getNumber())));

        Page<Card> cardPage = cardRepository.findAll(spec,
                PageRequest.of(request.getPage(), request.getSize()));

        List<CardResponse> cardResponses = cardPage.getContent().stream().map(card ->
                cardMapper.toCardResponse(card, userMapper.toUserDto(card.getUser()))).toList();

        return CardPageResponse.builder().cards(cardResponses)
                .page(cardPage.getNumber()).totalCount(cardPage.getTotalPages()).build();
    }

    public BigDecimal getBalance(UUID userId, UUID cardId) {
        return cardRepository.findCardBalanceByUser(userId, cardId).orElseThrow(() ->
                new EntityNotFoundException("Карта не найдена"));
    }

    public void transfer(UUID card1, UUID card2, BigDecimal price) {

    }

}
