package com.example.bankcards.dto.request;

import com.example.bankcards.entity.CardStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class CardFilterRequest extends PageRequest {

    private String number;
    private CardStatus status;

}
