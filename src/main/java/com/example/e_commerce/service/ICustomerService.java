package com.example.e_commerce.service;

import com.example.e_commerce.dto.customer.CustomerCreateDto;
import com.example.e_commerce.model.customer.Customer;

public interface ICustomerService {
    Customer create(CustomerCreateDto dto);
    Customer getOrThrow(Long id);

}
