package com.example.e_commerce.repo;

import com.example.e_commerce.model.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
    Customer findByEmail(String email);
    boolean existsByEmail(String email);
    @Query("select (count(c)>0) from CompanyCustomer c where c.taxId = :taxId")
    boolean existsByTaxId(@Param("taxId") String taxId);
}
