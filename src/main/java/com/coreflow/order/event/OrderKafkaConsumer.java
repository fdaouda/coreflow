package com.coreflow.order.event;

import com.coreflow.order.service.OrderProcessorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class OrderKafkaConsumer {
    private static final Logger log = LoggerFactory.getLogger(OrderKafkaConsumer.class);
    private final OrderProcessorService orderProcessorService;

    public OrderKafkaConsumer(OrderProcessorService orderProcessorService) {
        this.orderProcessorService = orderProcessorService;
    }

    @KafkaListener(topics = OrderProducer.TOPIC, groupId = "coreflow-group" )
    public void consume(@Payload OrderCreatedEvent orderCreatedEvent,
                        @Header(KafkaHeaders.RECEIVED_KEY) String key,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) int partition)
    {

        log.info(" [Kafka] Message reçu avec Key={}", key);


        orderProcessorService.process(orderCreatedEvent);
    }
}
