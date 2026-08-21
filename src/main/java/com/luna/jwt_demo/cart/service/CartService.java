package com.luna.jwt_demo.cart.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.luna.jwt_demo.cart.model.CartItemRequest;
import com.luna.jwt_demo.cart.repository.CartRepository;
import com.luna.jwt_demo.common.exception.custom.ResourceNotFoundException;
import com.luna.jwt_demo.product.model.ProductEntity;
import com.luna.jwt_demo.product.repository.ProductRepository;
import com.luna.jwt_demo.auth.model.entity.UserInfo;
import com.luna.jwt_demo.auth.repository.UserInfoRepository;
import com.luna.jwt_demo.cart.model.CartEntity;
import com.luna.jwt_demo.cart.model.CartItemEntity;

@Service
public class CartService {

    private CartRepository cartRepository;
    private ProductRepository productRepository;
    private UserInfoRepository userRepository;

    public CartService(
        CartRepository cartRepository,
        UserInfoRepository userRepository,
        ProductRepository productRepository
    ) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public void addCartItem(Long userId, CartItemRequest request) {
        CartEntity cart =  cartRepository.findByUserId(userId)
            .orElseGet(() -> {
                UserInfo user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User with ID {} does not exist!", userId));

                CartEntity newCart = new CartEntity();
                newCart.setUser(user);
                return cartRepository.save(newCart);
            });

        ProductEntity product = productRepository.findById(request.productId())
            .orElseThrow(() -> new ResourceNotFoundException("Product with ID {} does not exist!", request.productId()));

        Optional<CartItemEntity> existingItem = cart.getItems().stream()
            .filter(item -> item.getProduct().getId().equals(request.productId()))
            .findFirst();

        if (existingItem.isPresent()) {
            CartItemEntity item = existingItem.get();
            item.setQuantity(request.quantity());
        } else {
            CartItemEntity item = new CartItemEntity();
            item.setProduct(product);
            item.setCart(cart);
            item.setQuantity(request.quantity());
            cart.addItem(item);
        }
    }
}
