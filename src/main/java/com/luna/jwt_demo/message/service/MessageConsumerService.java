package com.luna.jwt_demo.message.service;

import java.util.Map;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import com.luna.jwt_demo.common.config.RabbitMqConfig;
import org.slf4j.LoggerFactory;

@Service
public class MessageConsumerService {

    private final static Logger log = LoggerFactory.getLogger(MessageConsumerService.class);

    // @RabbitListener(queues = RabbitMqConfig.QUEUE_NAME)
    // public void receiveMessage(Map<String, String> message) {
    //     log.info("Message: " + message.get("text"));
    // }
}
