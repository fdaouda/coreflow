package com.coreflow.order.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {
    private static final Logger log = LoggerFactory.getLogger(OrderConsumer.class);

    @KafkaListener(topics = OrderProducer.TOPIC, groupId = "coreflow-group" )
    public void consume(@Payload OrderCreatedEvent orderCreatedEvent,
                        @Header(KafkaHeaders.RECEIVED_KEY) String key,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) int partition)
    {

    }
}
