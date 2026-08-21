package com.luna.jwt_demo.cart.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.luna.jwt_demo.cart.model.CartItemRequest;
import com.luna.jwt_demo.cart.model.CartItemResponse;
import com.luna.jwt_demo.cart.service.CartService;

@RestController
@RequestMapping("/api/v1/carts")
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
    public ResponseEntity<String> addCartItem(
        Authentication authentication,
        @RequestBody CartItemRequest request
    ) {
        Long userId = (Long) authentication.getPrincipal();

        cartService.addCartItem(userId, request);
        return ResponseEntity.ok("Successfully added item to cart!");
    }
}
