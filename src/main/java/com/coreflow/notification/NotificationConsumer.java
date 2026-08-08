package com.coreflow.notification;

import com.coreflow.config.rabbitmq.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {
    private static final Logger log = LoggerFactory.getLogger(NotificationConsumer.class);

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void handleNotification (String message) {
        log.info("[RabbitMQ] Envoi notification (Email/SMS) : {}", message);
    }
}
