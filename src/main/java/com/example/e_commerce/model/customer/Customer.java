package com.example.e_commerce.model.customer;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "customer_type", discriminatorType = DiscriminatorType.STRING)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "customer")
public abstract class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email
    @NotBlank
    @Column(unique = true)
    private String email;

    private String phone;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="line1", column=@Column(name="bill_line1")),
            @AttributeOverride(name="line2", column=@Column(name="bill_line2")),
            @AttributeOverride(name="city",  column=@Column(name="bill_city")),
            @AttributeOverride(name="zip",   column=@Column(name="bill_zip")),
            @AttributeOverride(name="country",column=@Column(name="bill_country"))
    })
    private Address billingAddress;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="line1", column=@Column(name="ship_line1")),
            @AttributeOverride(name="line2", column=@Column(name="ship_line2")),
            @AttributeOverride(name="city",  column=@Column(name="ship_city")),
            @AttributeOverride(name="zip",   column=@Column(name="ship_zip")),
            @AttributeOverride(name="country",column=@Column(name="ship_country"))
    })
    private Address shippingAddress;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;
}
