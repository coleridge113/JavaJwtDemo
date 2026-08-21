package com.luna.jwt_demo.cart.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import com.luna.jwt_demo.product.model.ProductEntity;
import jakarta.persistence.Column;

@Entity
@Table(name = "cart_items")
public class CartItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private CartEntity cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @Column(nullable = false)
    private Integer quantity;

    public CartEntity getCart() { return this.cart; }
    public void setCart(CartEntity cart) { this.cart = cart; }
    public ProductEntity getProduct() { return this.product; }
    public void setProduct(ProductEntity product) { this.product = product; }
    public Integer getQuantity() { return this.quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Long getAmountInCents() { return this.product.getAmountInCents(); }
}
