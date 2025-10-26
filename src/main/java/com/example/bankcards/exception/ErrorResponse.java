package com.example.bankcards.exception;

import lombok.Builder;

@Builder
public record ErrorResponse(Integer code, String message) {
}
