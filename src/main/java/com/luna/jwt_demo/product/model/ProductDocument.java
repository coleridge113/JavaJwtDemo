package com.luna.jwt_demo.product.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Document(indexName = "products", createIndex = false)
public class ProductDocument {

    @Id
    private String id;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String name;

    @Field(type = FieldType.Long)
    private Long amountInCents;

    @Field(type = FieldType.Integer)
    private Integer stockQuantity;

    public ProductDocument(
        String id,
        String name,
        Long amountInCents,
        Integer stockQuantity
    ) {
        this.id = id;
        this.name = name;
        this.amountInCents = amountInCents;
        this.stockQuantity = stockQuantity;
    };

    public String getId() { return this.id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }
    public Long getAmountInCents() { return this.amountInCents; }
    public void setAmountInCents(Long amountInCents) { this.amountInCents = amountInCents; }
    public Integer getStockQuantity() { return this.stockQuantity; }
    public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }
}
