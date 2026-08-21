package com.luna.jwt_demo.order.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.luna.jwt_demo.common.config.RabbitMqConfig;
import com.luna.jwt_demo.common.exception.custom.EmptyCartException;
import com.luna.jwt_demo.common.exception.custom.ResourceNotFoundException;
import com.luna.jwt_demo.order.mapper.OrderMapper;
import com.luna.jwt_demo.order.model.CreateOrderRequest;
import com.luna.jwt_demo.order.model.OrderDto;
import com.luna.jwt_demo.order.model.OrderEntity;
import com.luna.jwt_demo.order.model.OrderItemEntity;
import com.luna.jwt_demo.order.repository.OrderRepository;
import com.luna.jwt_demo.product.mapper.ProductMapper;
import com.luna.jwt_demo.product.model.ProductEntity;
import com.luna.jwt_demo.product.service.ProductService;
import com.luna.jwt_demo.cart.model.CartEntity;
import com.luna.jwt_demo.cart.repository.CartRepository;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final ProductService productService;
    private final CartRepository cartRepository;
    private final RabbitTemplate rabbitTemplate;
    private final OrderMapper orderMapper;

    public OrderService(
        OrderRepository repository, 
        ProductService productService,
        CartRepository cartRepository,
        RabbitTemplate rabbitTemplate,
        OrderMapper orderMapper,
        ProductMapper productMapper
    ) {
        this.repository = repository;
        this.productService = productService;
        this.rabbitTemplate = rabbitTemplate;
        this.orderMapper = orderMapper;
        this.cartRepository = cartRepository;
    }

    @Transactional
    public void createOrder(Long userId) {
        CartEntity cart = cartRepository.findByUserId(userId)
            .orElseThrow(() -> new EmptyCartException("You have no items in your cart!"));

        if (cart.getItems().size() < 1) {
            throw new EmptyCartException("You have no items in your cart!");
        }

        OrderEntity order = new OrderEntity();
        order.setUser(cart.getUser());

        cart.getItems().forEach(cartItem -> {
            OrderItemEntity orderItem = new OrderItemEntity(
                order, 
                cartItem.getProduct(), 
                cartItem.getQuantity(), 
                cartItem.getAmountInCents()
            );

            order.addOrderItem(orderItem);
        });

        cart.clearCart();
    }

    public OrderDto getOrderById(Long orderId) {
        OrderEntity order = repository.findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order with ID {} does not exist!", orderId));

        return orderMapper.toDto(order);
    }
}
