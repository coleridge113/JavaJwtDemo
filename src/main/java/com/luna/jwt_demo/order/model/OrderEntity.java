package com.luna.jwt_demo.order.model;

import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import com.luna.jwt_demo.inventory.model.ProductEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemEntity> orderItems = new ArrayList<>();


    public OrderEntity(String customerName, List<OrderItemEntity> orderItems) {
        this.customerName = customerName;
        this.orderItems = orderItems;
    }

    public void addOrderItems(ProductEntity product, Integer quantity) {
        OrderItemEntity item = new OrderItemEntity(this, product, quantity);
        this.orderItems.add(item);
    }

    public Long getId() { return this.id; }
    public String getCustomerName() { return this.customerName; }
    public List<OrderItemEntity> getOrderItems() { return this.orderItems; }
}
