package com.example.bankcards.exception.handler;

import lombok.Builder;

@Builder
public record ErrorResponse(Integer code, String message) {
}
