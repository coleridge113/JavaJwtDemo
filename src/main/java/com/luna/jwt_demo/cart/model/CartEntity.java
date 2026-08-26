package com.luna.jwt_demo.cart.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import com.luna.jwt_demo.auth.model.entity.UserInfo;
import com.luna.jwt_demo.common.exception.custom.ResourceNotFoundException;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;

@Entity
@Table(name = "carts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, unique = true)
    private UserInfo user;

    @Column(nullable = false)
    private Long totalAmountInCents = 0L;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItemEntity> items = new ArrayList<>();

    public void addItem(CartItemEntity item) {
        items.add(item);
        item.setCart(this);
        recalculateTotal();
    }

    public void removeItem(CartItemEntity item) {
        items.remove(item);
        item.setCart(null);
        recalculateTotal();
    }

    public void removeItemById(Long cartItemId) {
        items.stream().filter(item -> item.getId().equals(cartItemId))
            .findFirst()
            .ifPresentOrElse(
                this::removeItem,
                () -> { 
                    throw new ResourceNotFoundException("Cart item with ID: {} does not exist!", cartItemId); 
                }
            );

        recalculateTotal();
    }

    public List<CartItemEntity> getItems() { return this.items; }
    public void clearCart() { this.items.clear(); }

    public void recalculateTotal() {
        totalAmountInCents = items.stream()
            .mapToLong(item -> item.getAmountInCents() * item.getQuantity())
            .sum();
    }
}
