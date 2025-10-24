package com.example.bankcards.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class UpdateUserRequest {

    private String name;

    private String lastname;

    private String middleName;

    private String phoneNumber;

}
