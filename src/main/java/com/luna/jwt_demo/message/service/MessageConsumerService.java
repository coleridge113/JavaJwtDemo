package com.luna.jwt_demo.message.service;

import java.util.Map;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import com.luna.jwt_demo.common.config.RabbitMqConfig;

@Service
public class MessageConsumerService {

    @RabbitListener(queues = RabbitMqConfig.QUEUE_NAME)
    public void receiveMessage(Map<String, String> message) {
        System.out.println(message);
    }
}
