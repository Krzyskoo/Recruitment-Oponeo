package com.example.e_commerce.dto.customer;

public record CustomerDetailsDto(Long id,
                                 String kind,
                                 String displayName,
                                 String email,
                                 String phone) {
}
