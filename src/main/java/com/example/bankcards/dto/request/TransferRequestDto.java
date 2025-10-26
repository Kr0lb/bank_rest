package com.example.bankcards.dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record TransferRequestDto(
        @NotNull UUID cardFrom,
        @NotNull UUID cardTo,
        @NotNull @Digits(integer = 9, fraction = 2) BigDecimal price
) {
}