package com.example.bankcards.controller;

import com.example.bankcards.dto.request.CardFilterRequest;
import com.example.bankcards.dto.request.PageRequest;
import com.example.bankcards.dto.request.TransferRequestDto;
import com.example.bankcards.dto.response.CardPageResponse;
import com.example.bankcards.dto.response.SuccessResponse;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.security.CustomUserDetails;
import com.example.bankcards.service.CardService;
import com.example.bankcards.util.CurrentUserProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cards")
public class CardController {

    private final CardService cardService;

    @PatchMapping("/status/{cardId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<SuccessResponse> changeStatus(@PathVariable UUID cardId, @RequestParam CardStatus status) {
        cardService.changeStatus(cardId, status);
        return ResponseEntity.ok(new SuccessResponse("Статус карты успешно изменен"));
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CardPageResponse> getAll(@Valid @ModelAttribute PageRequest pageRequest) {
        return ResponseEntity.ok(cardService.getAll(pageRequest));
    }


    @DeleteMapping("/{cardId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<SuccessResponse> deleteCardById(@PathVariable UUID cardId) {
        cardService.delete(cardId);
        return ResponseEntity.ok(new SuccessResponse("Карта успешно удалена"));
    }

    @GetMapping("/filter")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CardPageResponse> getByUserWithFilter(@Valid @ModelAttribute CardFilterRequest request) {
        CustomUserDetails userDetails = CurrentUserProvider.getCurrentUser();
        return ResponseEntity.ok(cardService.getCardByUserId(userDetails.getUserId(), request));
    }

    @GetMapping("/{cardId}/balance")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable UUID cardId) {
        CustomUserDetails userDetails = CurrentUserProvider.getCurrentUser();
        return ResponseEntity.ok(cardService.getBalance(userDetails.getUserId(), cardId));
    }

    @PostMapping("/transfer")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse> transfer(@Valid @ModelAttribute TransferRequestDto request) {
        CustomUserDetails userDetails = CurrentUserProvider.getCurrentUser();
        cardService.transfer(request.cardFrom(), request.cardTo(), userDetails.getUserId(), request.price());
        return ResponseEntity.ok(new SuccessResponse("Перевод прошёл успешно"));
    }

    @PatchMapping("/{cardId}/blocked")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessResponse> userBlockedCard(@PathVariable UUID cardId) {
        CustomUserDetails userDetails = CurrentUserProvider.getCurrentUser();
        cardService.blockCard(cardId, userDetails.getUserId());
        return ResponseEntity.ok(new SuccessResponse("Карта успешно заблокирована"));
    }

}
