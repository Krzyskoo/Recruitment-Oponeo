package com.example.e_commerce.dto.customer;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CustomerKindValidator.class)
public @interface ValidCustomerByKind {
    String message() default "Invalid customer data for given kind";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
