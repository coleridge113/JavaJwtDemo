package com.luna.jwt_demo.order.model;

import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String customerName;

    @Column(nullable = false, length = 50)
    private String items;

    public OrderEntity(String customerName, String items) {
        this.customerName = customerName;
        this.items = items;
    }

    public Long getId() { return this.id; }
    public String getCustomerName() { return this.customerName; }
    public String getItems() { return this.items; }
}
