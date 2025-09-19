package com.example.e_commerce.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductCreateDto(@NotBlank String name,
                               @NotNull BigDecimal netPrice,
                               @NotNull BigDecimal vatRate) {
}
