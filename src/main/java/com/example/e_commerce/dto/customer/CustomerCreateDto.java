package com.example.e_commerce.dto.customer;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CustomerCreateDto(@NotNull CustomerType kind,
                                String firstName,
                                String lastName,
                                String companyName,
                                String taxId,
                                @NotBlank @Email String email,
                                String phone,
                                @Valid AddressDto billingAddress,
                                @Valid AddressDto shippingAddress) {
}
