package com.luna.jwt_demo.order.model;

import com.luna.jwt_demo.inventory.model.ProductEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_items")
@NoArgsConstructor
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @Column(nullable = false)
    private Integer quantity;

    public OrderItemEntity(
        OrderEntity order,
        ProductEntity product,
        Integer quantity
    ) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
    }

    public Long getId() { return this.id; }
    public Integer getQuantity() { return this.quantity; }
    public OrderEntity getOrderEntity() { return this.order; }
    public ProductEntity getProductEntity() { return this.product; }
}
