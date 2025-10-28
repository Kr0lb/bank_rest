package com.example.bankcards.mapper;

import com.example.bankcards.dto.UserDto;
import com.example.bankcards.dto.response.CardResponse;
import com.example.bankcards.entity.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CardMapper {

    @Mapping(target = "number", expression = "java(com.example.bankcards.util.Masking.maskingCard(card.getNumber()))")
    @Mapping(target = "owner", source = "user")
    @Mapping(target = "id", source = "card.id")
    CardResponse toCardResponse(Card card, UserDto user);

}
