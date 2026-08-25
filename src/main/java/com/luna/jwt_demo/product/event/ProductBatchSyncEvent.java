package com.luna.jwt_demo.product.event;

import java.util.List;

public record ProductBatchSyncEvent(List<ProductSyncEvent> events) {}
