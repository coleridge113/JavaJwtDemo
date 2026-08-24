package com.luna.jwt_demo.email.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.luna.jwt_demo.common.config.RabbitMqConfig;
import com.luna.jwt_demo.order.model.OrderDto;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    private RabbitTemplate rabbitTemplate;

    public EmailService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = RabbitMqConfig.NOTIFICATION_QUEUE)
    public void receiveMessage(OrderDto order) {

        log.info("Message recieved via RabbitMQ");
        log.info(order.toString());

    }


    // @RabbitListener(queues = RabbitMqConfig.QUEUE_NAME)
    // public void receiveMessage(Map<String, String> message) {
    //     log.info("Message: " + message.get("text"));
    // }
}
