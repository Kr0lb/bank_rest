package com.example.bankcards.dto.request;

import lombok.Builder;

@Builder
public record AuthRequest(String username, String password) {
}
