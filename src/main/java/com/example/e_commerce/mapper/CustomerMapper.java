package com.example.e_commerce.mapper;

import com.example.e_commerce.dto.customer.AddressDto;
import com.example.e_commerce.dto.customer.CustomerCreateDto;
import com.example.e_commerce.dto.customer.CustomerDetailsDto;
import com.example.e_commerce.dto.customer.CustomerType;
import com.example.e_commerce.model.customer.Address;
import com.example.e_commerce.model.customer.CompanyCustomer;
import com.example.e_commerce.model.customer.Customer;
import com.example.e_commerce.model.customer.PersonCustomer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public Customer mapToCustomer(CustomerCreateDto customerCreateDto) {
        Customer customer;
        if (customerCreateDto.kind() == CustomerType.PERSON) {
            var p = new PersonCustomer();
            p.setFirstName(customerCreateDto.firstName());
            p.setLastName(customerCreateDto.lastName());
            customer = p;
        }else {
            var company = new CompanyCustomer();
            company.setCompanyName(customerCreateDto.companyName());
            company.setTaxId(customerCreateDto.taxId());
            customer = company;
        }
        customer.setEmail(customerCreateDto.email());
        customer.setPhone(customerCreateDto.phone());
        customer.setBillingAddress(toAddress(customerCreateDto.billingAddress()));
        customer.setShippingAddress(toAddress(customerCreateDto.shippingAddress()));
        return customer;
    }
    public CustomerDetailsDto toDto(Customer c) {
        String kind;
        String display;
        if (c instanceof PersonCustomer p) {
            kind = "PERSON";
            display = p.getFirstName() + " " + p.getLastName();
        } else if (c instanceof CompanyCustomer comp) {
            kind = "COMPANY";
            display = comp.getCompanyName();
        } else {
            kind = "UNKNOWN";
            display = "?";
        }
        return new CustomerDetailsDto(c.getId(), kind, display, c.getEmail(), c.getPhone());
    }
    private Address toAddress(AddressDto dto) {
        if (dto == null) return null;
        var a = new Address();
        a.setLine1(dto.line1());
        a.setLine2(dto.line2());
        a.setCity(dto.city());
        a.setZip(dto.zip());
        a.setCountry(dto.country());
        return a;
    }
}
