package com.example.e_commerce.model.customer;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Address {
    @NotBlank
    private String line1;
    private String line2;
    @NotBlank private String city;
    @NotBlank private String zip;
    @NotBlank private String country;
}
