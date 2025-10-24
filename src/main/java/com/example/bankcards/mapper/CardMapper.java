package com.example.bankcards.mapper;

import com.example.bankcards.dto.UserDto;
import com.example.bankcards.dto.response.CardResponse;
import com.example.bankcards.entity.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CardMapper {

    @Mapping(target = "owner", source = "user")
    CardResponse toCardResponse(Card card, UserDto user);

}
