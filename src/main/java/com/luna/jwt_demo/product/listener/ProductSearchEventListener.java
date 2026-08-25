package com.luna.jwt_demo.product.listener;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;

import com.luna.jwt_demo.product.model.ProductDocument;
import com.luna.jwt_demo.product.event.ProductBatchSyncEvent;
import com.luna.jwt_demo.product.event.ProductSyncEvent;
import com.luna.jwt_demo.product.mapper.ProductMapper;
import com.luna.jwt_demo.product.repository.ProductSearchRepository;

@Component
public class ProductSearchEventListener {

    private final static Logger log = LoggerFactory.getLogger(ProductSearchEventListener.class);
    private final ProductSearchRepository searchRepository;
    private final ProductMapper productMapper;

    public ProductSearchEventListener(
        ProductSearchRepository searchRepository,
        ProductMapper productMapper
    ) {
        this.searchRepository = searchRepository;
        this.productMapper = productMapper;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleProductSync(ProductSyncEvent event) {
        log.info("Received ProductSyncEvent for product ID: {}", event.id());

        if (event.type() == ProductSyncEvent.EventType.DELETED) {
            searchRepository.deleteById(event.id().toString());
            log.info("Deleted product document ID: {} from Elastisearch", event.id());
            return;
        }

        ProductDocument doc = productMapper.toDocument(event);

        searchRepository.save(doc);
        log.info("Indexed product document ID: {} to Elastisearch", event.id());
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleBatchProductSync(ProductBatchSyncEvent batchEvent) {
        List<ProductSyncEvent> events = batchEvent.events();
        if(events == null || events.isEmpty()) return;

        List<ProductDocument> docs = events.stream()
            .filter(event -> event.type() == ProductSyncEvent.EventType.CREATED_OR_UPDATED)
            .map(productMapper::toDocument)
        .toList();

        if (!docs.isEmpty()) {
            searchRepository.saveAll(docs);
            log.info("Indexed multiple products with count: {}", docs.size());
        }
    }
}
