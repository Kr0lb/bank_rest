package com.example.bankcards.dto.response;

import com.example.bankcards.dto.UserDto;
import com.example.bankcards.entity.Status;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record CardResponse(String number, UserDto owner, LocalDate validityPeriod, Status status, BigDecimal balance) {

}
