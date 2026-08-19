package com.luna.jwt_demo.product.controller;

import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.luna.jwt_demo.product.model.ProductDto;
import com.luna.jwt_demo.product.service.ProductService;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> addProduct(@RequestBody ProductDto productDto) {
        productService.addProduct(productDto);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(Map.of("messsage", "successfully created"));
    }

    @PostMapping("/batch")
    public ResponseEntity<Map<String, String>> addProducts(@RequestBody List<ProductDto> products) {
        productService.addProducts(products);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(Map.of("messsage", "Successfully added batch products"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") Long productId) {
        ProductDto product = productService.findProductById(productId);

        return ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity<Page<ProductDto>> getProducts(
        @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<ProductDto> productsPage = productService.getProducts(pageable);

        return ResponseEntity.ok(productsPage);
    }
}
