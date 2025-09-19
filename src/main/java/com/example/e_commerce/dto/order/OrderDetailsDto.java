package com.example.e_commerce.dto.order;

import java.math.BigDecimal;
import java.util.List;

public record OrderDetailsDto(String id,
                              CustomerSummary customer,
                              List<Item> items,
                              BigDecimal totalNet,
                              BigDecimal totalGross) {
    public record CustomerSummary(Long id, String kind, String displayName, String email) {}
    public record Item(String productName, int quantity, BigDecimal unitNet, BigDecimal lineNet, BigDecimal lineGross) {}
}
