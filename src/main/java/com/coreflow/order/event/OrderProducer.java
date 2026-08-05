package com.coreflow.order.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderProducer {
    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;
    public static final String TOPIC = "orders.order-created";
    private static final Logger log = LoggerFactory.getLogger(OrderProducer.class);

    public OrderProducer(KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderCreated(OrderCreatedEvent event) {
        log.info("Évènement OrderCreated émit pour la commande : {}",event.orderId());
        kafkaTemplate.send(TOPIC, event.orderId().toString(), event);
    }

}
