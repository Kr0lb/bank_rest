package com.example.bankcards.dto.response;

import lombok.Builder;

@Builder
public record AuthResponse(String token) {

}
