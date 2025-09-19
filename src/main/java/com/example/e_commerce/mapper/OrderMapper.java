package com.example.e_commerce.mapper;

import com.example.e_commerce.dto.order.OrderDetailsDto;
import com.example.e_commerce.model.customer.CompanyCustomer;
import com.example.e_commerce.model.customer.Customer;
import com.example.e_commerce.model.customer.PersonCustomer;
import com.example.e_commerce.model.order.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public OrderDetailsDto toDetailsDto(Order o) {
        var cust = o.getCustomer();
        var summary = new OrderDetailsDto.CustomerSummary(
                cust.getId(),
                kindOf(cust),
                displayNameOf(cust),
                cust.getEmail()
        );

        var items = o.getItems().stream().map(i ->
                new OrderDetailsDto.Item(
                        i.getProduct().getName(),
                        i.getQuantity(),
                        i.getUnitNet(),
                        i.getLineNet(),
                        i.getLineGross()
                )
        ).toList();

        return new OrderDetailsDto(
                o.getId(),
                summary,
                items,
                o.getTotalNet(),
                o.getTotalGross()
        );
    }

    private String kindOf(Customer c) {
        if (c instanceof PersonCustomer) return "PERSON";
        if (c instanceof CompanyCustomer) return "COMPANY";
        return "UNKNOWN";
    }

    private String displayNameOf(Customer c) {
        if (c instanceof PersonCustomer p) {
            return (p.getFirstName() == null ? "" : p.getFirstName()) + " " +
                    (p.getLastName()  == null ? "" : p.getLastName());
        }
        if (c instanceof CompanyCustomer comp) {
            return comp.getCompanyName();
        }
        return "?";
    }

}
