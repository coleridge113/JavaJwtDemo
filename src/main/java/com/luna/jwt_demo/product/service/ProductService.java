package com.luna.jwt_demo.product.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.luna.jwt_demo.common.config.RabbitMqConfig;
import com.luna.jwt_demo.common.exception.custom.ResourceNotFoundException;
import com.luna.jwt_demo.order.model.OrderDto;
import com.luna.jwt_demo.product.mapper.ProductMapper;
import com.luna.jwt_demo.product.model.InventoryResult;
import com.luna.jwt_demo.product.model.ProductDocument;
import com.luna.jwt_demo.product.model.ProductDto;
import com.luna.jwt_demo.product.model.ProductEntity;
import com.luna.jwt_demo.product.event.ProductBatchSyncEvent;
import com.luna.jwt_demo.product.event.ProductSyncEvent;
import com.luna.jwt_demo.product.repository.ProductRepository;
import com.luna.jwt_demo.product.repository.ProductSearchRepository;

@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final ProductSearchRepository searchRepository;
    private final ApplicationEventPublisher eventPublisher;

    public ProductService(
        ProductMapper productMapper, 
        ProductRepository repository,
        ProductSearchRepository searchRepository,
        ApplicationEventPublisher eventPublisher
    ) {
        this.productMapper = productMapper;
        this.productRepository = repository;
        this.searchRepository = searchRepository;
        this.eventPublisher = eventPublisher;
    }

    @RabbitListener(queues = RabbitMqConfig.INVENTORY_QUEUE)
    public void productQueueListener(OrderDto order) {
        log.info("Inventory Queue");
        log.info(order.toString());
    }

    @Transactional
    public InventoryResult updateProductStock(Long productId, Integer quantity, boolean isAdd) {
        ProductEntity product = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product with ID {} does not exist!", productId));

        if (product.getStockQuantity() < quantity) {
            return new InventoryResult(false, "Not enough stock!");
        }

        Integer newStockQuantity = product.getStockQuantity() - quantity;

        product.setStockQuantity(newStockQuantity);

        ProductEntity savedProduct = productRepository.save(product);
        eventPublisher.publishEvent(ProductSyncEvent.createdOrUpdated(savedProduct));

        return new InventoryResult(true, "Successfully update stock!");
    }

    public Integer getProductStock(Long productId) {
        ProductEntity product = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product with ID {} does not exist!", productId));

        return product.getStockQuantity();
    }

    @Transactional
    public void addProduct(ProductDto dto) {
        ProductEntity entity = productMapper.toEntity(dto);

        ProductEntity savedProduct = productRepository.save(entity);
        eventPublisher.publishEvent(ProductSyncEvent.createdOrUpdated(savedProduct));
    }

    @Transactional
    public void addProducts(List<ProductDto> dtos) {
        List<ProductEntity> entities = dtos.stream()
            .map(productMapper::toEntity)
            .toList();
        List<ProductEntity> savedEntities = productRepository.saveAll(entities);
        List<ProductSyncEvent> syncEvents = ProductSyncEvent.batchCreatedOrUpdated(savedEntities);
        eventPublisher.publishEvent(new ProductBatchSyncEvent(syncEvents));
    }

    public ProductDto findProductById(Long productId) {
        ProductEntity entity = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product with ID {} does not exist!", productId));
    
        return productMapper.toDto(entity);
    }

    public ProductEntity findProductEntityById(Long productId) {
        ProductEntity entity = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product with ID {} does not exist!", productId));

        return entity;
    }

    @Transactional(readOnly = true)
    public Page<ProductDto> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable).map(productMapper::toDto);
    }

    public List<ProductDto> searchProducts(String keyword) {
        List<ProductDocument> documents = searchRepository.findByNameContaining(keyword);
        return documents.stream()
            .map(productMapper::toDto)
            .toList();
    }

    @Transactional
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
        eventPublisher.publishEvent(ProductSyncEvent.deleted(productId));
    }
}
