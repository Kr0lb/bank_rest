package com.example.bankcards.dto.request;

import com.example.bankcards.entity.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class CardFilterRequest extends PageRequest {

    private String number;
    private Status status;

}
