package com.coreflow.order.event;

import com.coreflow.order.service.OrderProcessorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
public class OrderKafkaConsumer {
    private static final Logger log = LoggerFactory.getLogger(OrderKafkaConsumer.class);
    private final OrderProcessorService orderProcessorService;

    public OrderKafkaConsumer(OrderProcessorService orderProcessorService) {
        this.orderProcessorService = orderProcessorService;
    }

    @RetryableTopic(attempts = "5",
    backoff = @Backoff(delay = 1000, multiplier = 2.0),
    autoCreateTopics = "true")
    @KafkaListener(topics = OrderProducer.TOPIC, groupId = "coreflow-group" )
    public void consume(@Payload OrderCreatedEvent orderCreatedEvent,
                        @Header(KafkaHeaders.RECEIVED_KEY) String key,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) int partition)
    {

        log.info(" [Kafka] Message reçu avec Key={}", key);


        orderProcessorService.process(orderCreatedEvent);
    }


    /**
     * Cette méthode est appelée automatiquement uniquement si les 3 tentatives échouent.
     * Le message se trouve maintenant dans le topic DLQ (ex: orders-dlt).
     */
    @DltHandler
    public void handleDltMessage(@Payload OrderCreatedEvent event,
                                 @Header(KafkaHeaders.RECEIVED_TOPIC) String originalTopic) {
        log.error(" [DLQ] Événement définitivement échoué et routé vers la DLQ ! Topic origine={}, EventID={}",
                originalTopic, event.orderId());

        // Exemples d'actions en prod : envoyer une alerte Slack, enregistrer l'échec dans une table 'failed_events', etc.
    }
}
