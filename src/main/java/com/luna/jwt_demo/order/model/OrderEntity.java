package com.luna.jwt_demo.order.model;

import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import com.luna.jwt_demo.auth.model.entity.UserInfo;
import com.luna.jwt_demo.product.model.ProductEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserInfo user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemEntity> orderItems = new ArrayList<>();

    public void addOrderItem(OrderItemEntity orderItem) {
        if (orderItem != null) {
            orderItems.add(orderItem);
            orderItem.setOrder(this);
        }
    }

    public void addOrderItem(ProductEntity product, Integer quantity) {
        OrderItemEntity orderItem = new OrderItemEntity(
            this, 
            product, 
            quantity, 
            product.getAmountInCents()
        );
        this.orderItems.add(orderItem);
    }

    public Long getId() { return this.id; }
    public Long getUserId() { return this.user.getId(); }
    public void setUser(UserInfo user) { this.user = user; }
    public List<OrderItemEntity> getOrderItems() { return this.orderItems; }

}
