package com.example.e_commerce.model.order;
import com.example.e_commerce.model.customer.Customer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import de.huxhorn.sulky.ulid.ULID;


import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id
    @Column(length = 26)
    private String id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal totalNet;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal totalGross;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    void prePersist() {
        if (this.id == null) this.id = new ULID().nextULID();
        this.createdAt = Instant.now();
    }

    public void addItem(OrderItem i) {
        i.setOrder(this);
        items.add(i);
    }
}
