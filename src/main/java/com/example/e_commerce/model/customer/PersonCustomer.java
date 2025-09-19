package com.example.e_commerce.model.customer;

import com.example.e_commerce.model.customer.Customer;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("PERSON")
public class PersonCustomer extends Customer {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
}
