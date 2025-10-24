package com.example.bankcards.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class PageRequest {

    @Size(min = 1, message = "Номер странницы не может быть отрицательным или 0")
    private Integer page = 1;
    @Size(min = 10, message = "Количество элементов на странице не может быть меньше 10")
    private Integer size = 20;

    public Integer getPage() {
        return page - 1;
    }

}
