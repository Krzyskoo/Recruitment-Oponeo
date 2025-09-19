package com.example.e_commerce.dto.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderCreateDto(@NotNull Long customerId,
                             @NotEmpty List<Item> items) {
    public record Item(@NotNull Long productId, @Min(1) int quantity) {
    }
}
