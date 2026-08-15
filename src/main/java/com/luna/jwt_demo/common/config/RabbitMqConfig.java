package com.luna.jwt_demo.common.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    // Exchange & Routing Constants
    public static final String ORDERS_EXCHANGE = "orders.topic.exchange";
    
    // Queues
    public static final String INVENTORY_QUEUE = "orders.inventory.queue";
    public static final String NOTIFICATION_QUEUE = "orders.notification.queue";
    public static final String ANALYTICS_QUEUE = "orders.analytics.queue";

    // Routing Patterns
    public static final String ORDER_CREATED_PATTERN = "orders.created";
    public static final String ORDER_EVENTS_PATTERN = "orders.#";
    public static final String NOTIFICATION_PATTERN = "orders.*.notification"; 

    // --- Global Infrastructure ---
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public TopicExchange ordersExchange() {
        return new TopicExchange(ORDERS_EXCHANGE);
    }

    // --- Queues ---
    @Bean
    public Queue inventoryQueue() {
        return QueueBuilder.durable(INVENTORY_QUEUE).build();
    }

    @Bean
    public Queue notificationQueue() {
        return QueueBuilder.durable(NOTIFICATION_QUEUE).build();
    }

    @Bean
    public Queue analyticsQueue() {
        return QueueBuilder.durable(ANALYTICS_QUEUE).build();
    }

    // --- Bindings (Binding Multiple Queues to One Exchange) ---
    @Bean
    public Binding inventoryBinding(@Qualifier("inventoryQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ORDER_CREATED_PATTERN);
    }

    @Bean
    public Binding notificationBinding(@Qualifier("notificationQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ORDER_EVENTS_PATTERN);
    }

    @Bean
    public Binding analyticsBinding(@Qualifier("analyticsQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ORDER_EVENTS_PATTERN);
    }
}
