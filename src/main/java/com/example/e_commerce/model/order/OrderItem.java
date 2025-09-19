package com.example.e_commerce.model.order;
import com.example.e_commerce.model.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "order_item")
public class OrderItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Order order;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Product product;

    @Min(1)
    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal unitNet;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal lineNet;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal lineGross;
}
