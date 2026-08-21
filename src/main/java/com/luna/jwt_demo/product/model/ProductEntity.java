package com.luna.jwt_demo.product.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Min(value = 0, message = "Quantity can't be negative")
    @NotNull
    @Column(nullable = false)
    private Integer stockQuantity;

    @Min(value = 0, message = "Amount can't be negative")
    @Column(nullable = false)
    private Long amountInCents;

    public ProductEntity(String name, Integer stockQuantity) {
        this.name = name;
        this.stockQuantity = stockQuantity;
    }

    public Long getId() { return this.id; }
    public String getName() { return this.name; }
    public Integer getStockQuantity() { return this.stockQuantity; }
    public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }
    public Long getAmount() { return this.amountInCents; }
}
