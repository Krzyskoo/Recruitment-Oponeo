package com.example.e_commerce.model.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true, length = 255)
    private String name;

    @NotNull
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal netPrice;

    @NotNull
    @Column(nullable = false, precision = 5, scale = 4)
    private BigDecimal vatRate;
}
