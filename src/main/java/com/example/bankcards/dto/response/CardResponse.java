package com.example.bankcards.dto.response;

import com.example.bankcards.dto.UserDto;
import com.example.bankcards.entity.CardStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Builder
public record CardResponse(String number, UserDto owner, LocalDate validityPeriod, CardStatus status, BigDecimal balance,
                           UUID id) {

}
