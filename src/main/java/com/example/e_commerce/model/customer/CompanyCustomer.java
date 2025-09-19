package com.example.e_commerce.model.customer;

import com.example.e_commerce.model.customer.Customer;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("COMPANY")
public class CompanyCustomer extends Customer {
    @NotBlank
    @Column()
    private String companyName;

    @NotBlank
    @Column(unique = true, length = 32)
    private String taxId;
}
