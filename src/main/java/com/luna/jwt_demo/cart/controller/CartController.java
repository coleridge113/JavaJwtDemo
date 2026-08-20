package com.luna.jwt_demo.cart.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.luna.jwt_demo.cart.model.CartItemResponse;
import com.luna.jwt_demo.cart.service.CartService;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // @GetMapping
    // public ResponseEntity<List<CartItemResponse>> getCartItems() {
    //
    // }

    @PostMapping
    public ResponseEntity<String> addCartItems() {
        return ResponseEntity.ok("Successfully added items to cart!");
    }
}
