package com.example.e_commerce.dto.customer;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CustomerKindValidator implements ConstraintValidator<ValidCustomerByKind, CustomerCreateDto> {
    public boolean isValid(CustomerCreateDto d, ConstraintValidatorContext c) {
        if (d == null || d.kind() == null) return false;
        return switch (d.kind()) {
            case PERSON  -> notBlank(d.firstName()) && notBlank(d.lastName());
            case COMPANY -> notBlank(d.companyName()) && notBlank(d.taxId());
        };
    }
    private boolean notBlank(String s) { return s != null && !s.isBlank(); }
}
