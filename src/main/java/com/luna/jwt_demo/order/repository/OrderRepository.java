package com.luna.jwt_demo.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.luna.jwt_demo.order.model.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

}
