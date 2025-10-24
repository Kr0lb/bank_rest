package com.example.bankcards.controller;

import com.example.bankcards.dto.request.CardFilterRequest;
import com.example.bankcards.dto.request.PageRequest;
import com.example.bankcards.dto.response.CardPageResponse;
import com.example.bankcards.entity.Status;
import com.example.bankcards.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cards")
public class CardController {

    private final CardService cardService;

    @PatchMapping("/status/{cardId}")
    public void changeStatus(@PathVariable UUID cardId, @RequestParam Status status) {
        cardService.changeStatus(cardId, status);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CardPageResponse> getAll(@Valid PageRequest pageRequest) {
        return ResponseEntity.ok(cardService.getAll(pageRequest));
    }

    @DeleteMapping("/{cardId}")
    public void deleteCardById(@PathVariable UUID cardId) {
        cardService.delete(cardId);
    }

    @GetMapping("/filter")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<CardPageResponse> getByUserWithFilter(Principal principal, @Valid CardFilterRequest request) {
        return ResponseEntity.ok(cardService.getCardByUser(principal.getName(), request));
    }

}
