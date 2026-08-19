package com.luna.jwt_demo.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.luna.jwt_demo.product.model.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    Page<ProductEntity> findAll(Pageable pageable);
}
