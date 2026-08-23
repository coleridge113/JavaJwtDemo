package com.luna.jwt_demo.order.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.luna.jwt_demo.common.config.RabbitMqConfig;
import com.luna.jwt_demo.common.exception.custom.EmptyCartException;
import com.luna.jwt_demo.common.exception.custom.ResourceNotFoundException;
import com.luna.jwt_demo.order.mapper.OrderMapper;
import com.luna.jwt_demo.order.model.OrderDto;
import com.luna.jwt_demo.order.model.OrderEntity;
import com.luna.jwt_demo.order.model.OrderItemEntity;
import com.luna.jwt_demo.order.model.OrderItemResponse;
import com.luna.jwt_demo.order.repository.OrderRepository;
import com.luna.jwt_demo.product.mapper.ProductMapper;
import com.luna.jwt_demo.product.model.ProductEntity;
import com.luna.jwt_demo.product.service.ProductService;
import com.luna.jwt_demo.cart.model.CartEntity;
import com.luna.jwt_demo.cart.repository.CartRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final CartRepository cartRepository;
    private final RabbitTemplate rabbitTemplate;
    private final OrderMapper orderMapper;

    public OrderService(
        OrderRepository orderRepository, 
        ProductService productService,
        CartRepository cartRepository,
        RabbitTemplate rabbitTemplate,
        OrderMapper orderMapper,
        ProductMapper productMapper
    ) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.rabbitTemplate = rabbitTemplate;
        this.orderMapper = orderMapper;
        this.cartRepository = cartRepository;
    }

    @Transactional
    public Long createOrder(Long userId) {
        CartEntity cart = cartRepository.findByUserId(userId)
            .orElseThrow(() -> new EmptyCartException("You have no items in your cart!"));

        if (cart.getItems().size() < 1) {
            throw new EmptyCartException("You have no items in your cart!");
        }

        OrderEntity order = new OrderEntity();
        order.setUser(cart.getUser());

        cart.getItems().forEach(cartItem -> {
            ProductEntity product = cartItem.getProduct();
            Integer quantity = cartItem.getQuantity();

            OrderItemEntity orderItem = new OrderItemEntity(
                order, 
                product,
                quantity,
                cartItem.getAmountInCents()
            );

            productService.updateProductStock(product.getId(), quantity, false);
            order.addOrderItem(orderItem);
        });

        cart.clearCart();
        OrderEntity savedOrder = orderRepository.save(order);

        return savedOrder.getId();
    }

    public OrderDto getOrderById(Long orderId, List<String> includes) {
        OrderEntity order = orderRepository.findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order with ID {} does not exist!", orderId));

        List<Long> orderItemIdList = null;
        List<OrderItemResponse> orderItems = null;

        if (includes != null && includes.contains("items")) {
            orderItems = order.getOrderItems().stream().map(item -> {
                Integer quantity = item.getQuantity();
                Long amountInCents = item.getAmountInCents();
                Long totalAmountInCents = quantity * amountInCents;

                return new OrderItemResponse(
                    item.getId(),
                    item.getProductEntity().getName(),
                    quantity,
                    amountInCents,
                    totalAmountInCents
                );
            })
            .toList();

        } else {
            orderItemIdList = order.getOrderItems().stream()
                .map(OrderItemEntity::getId)
                .toList();
        }

        return new OrderDto(
            order.getId(),
            order.getUserId(),
            orderItemIdList,
            orderItems
        );
    }
}
