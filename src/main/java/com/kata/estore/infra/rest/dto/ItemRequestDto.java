package com.kata.estore.infra.rest.dto;

import com.kata.estore.domain.model.ItemType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ItemRequestDto(
        @NotNull
        String model,
        @NotNull
        ItemType type,
        @NotNull
        @Min(0)
        Integer quantity
) {
}
