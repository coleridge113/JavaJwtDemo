package com.luna.jwt_demo.inventory.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import com.luna.jwt_demo.common.config.RabbitMqConfig;
import com.luna.jwt_demo.order.model.OrderDto;

@Service
public class InventoryService {

    private static final Logger log = LoggerFactory.getLogger(InventoryService.class);

    @RabbitListener(queues = RabbitMqConfig.INVENTORY_QUEUE)
    private void inventoryQueueListener(OrderDto orderDto) {
        try {
            Thread.sleep(3000);
            log.info("Inventory Service");
            log.info("{}", orderDto);
        } catch (Exception ex) {
            log.error("Error: {}", ex.getMessage());
        }
    }
}
