package com.luna.jwt_demo.order.model;

import org.hibernate.annotations.Audited.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order")
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String customerName;

    @Column(nullable = false, length = 50)
    private String items;

    public OrderEntity(Long id, String customerName, String items) {
        this.id = id;
        this.customerName = customerName;
        this.items = items;
    }
}
