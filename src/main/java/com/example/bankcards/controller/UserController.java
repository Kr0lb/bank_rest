package com.example.bankcards.controller;

import com.example.bankcards.dto.request.UpdateUserByAdminRequest;
import com.example.bankcards.dto.response.SuccessResponse;
import com.example.bankcards.service.CardService;
import com.example.bankcards.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final CardService cardService;

    @PostMapping("/{userId}/cards")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<SuccessResponse> createCard(@PathVariable UUID userId) {
        cardService.create(userId);
        return ResponseEntity.ok(new SuccessResponse("Карта успешно создана"));
    }

    @PatchMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<SuccessResponse> updateUserByAdmin(@PathVariable UUID userId, @Valid @RequestBody UpdateUserByAdminRequest request) {
        userService.updateUserByAdmin(userId, request);
        return ResponseEntity.ok(new SuccessResponse("Данные пользователя успешно обновлены"));
    }

}
