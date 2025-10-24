package com.example.bankcards.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class UpdateUserRequest {

    @Pattern(regexp = "[А-ЯЁ][а-яё]{29}", message = "Имя должно начинаться с заглавной буквы и не может быть больше 30 символов")
    private String firstname;

    @Pattern(regexp = "[А-ЯЁ][а-яё]{29}", message = "Фамилия должна начинаться с заглавной буквы и не может быть больше 30 символов")
    private String lastname;

    @Pattern(regexp = "[А-ЯЁ][а-яё]{29}", message = "Отчество должно начинаться с заглавной буквы и не может быть больше 30 символов")
    private String middleName;

    @Pattern(regexp = "\\d{10}", message = "Номер должен быть в формате '**********'")
    private String phoneNumber;

}
