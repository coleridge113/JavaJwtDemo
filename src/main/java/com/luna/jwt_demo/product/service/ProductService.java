package com.luna.jwt_demo.product.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import com.luna.jwt_demo.common.config.RabbitMqConfig;
import com.luna.jwt_demo.common.exception.custom.ResourceNotFoundException;
import com.luna.jwt_demo.order.model.OrderDto;
import com.luna.jwt_demo.product.mapper.ProductMapper;
import com.luna.jwt_demo.product.model.ProductDto;
import com.luna.jwt_demo.product.model.ProductEntity;
import com.luna.jwt_demo.product.repository.ProductRepository;

@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final ProductMapper productMapper;
    private final ProductRepository repository;

    public ProductService(ProductMapper productMapper, ProductRepository repository) {
        this.productMapper = productMapper;
        this.repository = repository;
    }

    @RabbitListener(queues = RabbitMqConfig.INVENTORY_QUEUE)
    private void productQueueListener(OrderDto orderDto) {
        try {
            Thread.sleep(3000);
            log.info("Product Service");
            log.info("{}", orderDto);
        } catch (Exception ex) {
            log.error("Error: {}", ex.getMessage());
        }
    }

    public boolean checkQuantity(String name) {
        return false;
    }

    public void addProduct(ProductDto dto) {
        ProductEntity entity = productMapper.toEntity(dto);
        repository.save(entity);
    }

    public ProductDto findProductById(Long productId) {
        ProductEntity entity = repository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product with ID {} does not exist!", productId));

        return productMapper.toDto(entity);
    }
}
