package com.example.e_commerce.dto.customer;

import jakarta.validation.constraints.NotBlank;

public record AddressDto(@NotBlank String line1,
                         String line2,
                         @NotBlank String city,
                         @NotBlank String zip,
                         @NotBlank String country) {

}
