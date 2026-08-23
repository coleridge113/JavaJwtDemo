package com.luna.jwt_demo.cart.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.luna.jwt_demo.cart.model.CartItemRequest;
import com.luna.jwt_demo.cart.model.CartItemDto;
import com.luna.jwt_demo.cart.service.CartService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/v1/carts")
public class CartController {

    private final static Logger log = LoggerFactory.getLogger(CartController.class);
    private final ObjectMapper objectMapper;
    private final CartService cartService;

    public CartController(
        CartService cartService,
        ObjectMapper objectMapper
    ) {
        this.cartService = cartService;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public ResponseEntity<List<CartItemDto>> getCartItems(Authentication authentication) throws JsonProcessingException {
        Long userId = (Long) authentication.getPrincipal();

        List<CartItemDto> items = cartService.getCartItems(userId);

        String json = objectMapper.writeValueAsString(items);
        log.info(json);
        return ResponseEntity.ok(items);
    }

    @PutMapping("/items")
    public ResponseEntity<String> upsertCartItem(
        Authentication authentication,
        @RequestBody CartItemRequest request
    ) {
        Long userId = (Long) authentication.getPrincipal();

        cartService.upsertCartItem(userId, request);
        return ResponseEntity.ok("Successfully added item to cart!");
    }
}
