package com.example.bankcards.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UserDto(UUID id, String fio) {

}
