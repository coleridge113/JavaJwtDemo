package com.luna.jwt_demo.cart.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.luna.jwt_demo.cart.model.CartEntity;

public interface CartRepository extends JpaRepository<CartEntity, Long> {

    public Optional<CartEntity> findByUserId(Long userId);

}
