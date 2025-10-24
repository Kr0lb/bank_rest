package com.example.bankcards.controller;

import com.example.bankcards.dto.request.UpdateUserByAdminRequest;
import com.example.bankcards.service.CardService;
import com.example.bankcards.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final CardService cardService;

    @PostMapping("/{userId}/cards")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void createCard(@PathVariable UUID userId) {
        cardService.create(userId);
    }

    @PatchMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateUserByAdmin(@PathVariable UUID userId, @Valid @RequestBody UpdateUserByAdminRequest request) {
        userService.updateUserByAdmin(userId, request);
    }

}
