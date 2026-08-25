package com.luna.jwt_demo.email.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.luna.jwt_demo.common.config.RabbitMqConfig;
import com.luna.jwt_demo.order.model.OrderDto;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    @RabbitListener(queues = RabbitMqConfig.NOTIFICATION_QUEUE)
    public void receiveMessage(OrderDto order) {

        log.info("Message recieved via RabbitMQ");
        log.info(order.toString());

    }
}
