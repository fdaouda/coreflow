package com.coreflow.order.service;

import com.coreflow.config.RabbitMQConfig;
import com.coreflow.order.event.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProcessorService {
    private final RabbitTemplate rabbitTemplate;
    private static final Logger log = LoggerFactory.getLogger(OrderProcessorService.class);

    public OrderProcessorService(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    public void process(OrderCreatedEvent event) {
        log.info(" [Processor] Traitement métier pour OrderId={}", event.orderId());

        // Relais vers RabbitMQ pour la tâche d'envoi de notification
        String msg = "Order confirmation for: " + event.orderId();


        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, msg);


        log.info(" [OrderProcessor] Tâche de notification transmise à RabbitMQ.");

    }
}
