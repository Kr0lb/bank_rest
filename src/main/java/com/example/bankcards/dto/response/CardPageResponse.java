package com.example.bankcards.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CardPageResponse {

    private List<CardResponse> cards;
    private Integer page;
    private Integer totalCount;

}
