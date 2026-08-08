package com.coreflow.order.service;

import com.coreflow.config.rabbitmq.RabbitMQConfig;
import com.coreflow.order.domain.Order;
import com.coreflow.order.domain.OrderStatus;
import com.coreflow.order.domain.ProcessedEvent;
import com.coreflow.order.event.OrderCreatedEvent;
import com.coreflow.order.repository.OrderRepository;
import com.coreflow.order.repository.ProcessedEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderProcessorService {
    private final RabbitTemplate rabbitTemplate;
    private final ProcessedEventRepository processedEventRepository;
    private final OrderRepository orderRepository;
    private static final Logger log = LoggerFactory.getLogger(OrderProcessorService.class);

    public OrderProcessorService(RabbitTemplate rabbitTemplate,
                                 ProcessedEventRepository processedEventRepository,
                                 OrderRepository orderRepository){

        this.rabbitTemplate = rabbitTemplate;
        this.processedEventRepository = processedEventRepository;
        this.orderRepository = orderRepository;

    }

    @Transactional
    public void process(OrderCreatedEvent event) {

        if (processedEventRepository.existsByEventId(event.orderId())) {
            log.warn(" [Idempotence] l'event OrderId={} est ignoré car il a déjà été traité par Kafka", event.orderId());
            return;
        }

        log.info(" [Processor] Traitement métier pour OrderId={}", event.orderId());


        Order order = orderRepository.findById(event.orderId())
                .orElseThrow(() -> new IllegalArgumentException("Commande non trouvé avec l'ID : " + event.orderId()));

        order.transitionTo(OrderStatus.PROCESSING);

        // Relais vers RabbitMQ pour la tâche d'envoi de notification
        String msg = "Order confirmation for: " + event.orderId();

        order.transitionTo(OrderStatus.COMPLETED);
        orderRepository.save(order);


        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, msg);
        log.info(" [OrderProcessor] Tâche de notification transmise à RabbitMQ.");

        // 4. MARQUER L'ÉVÉNEMENT COMME TRAITÉ
        processedEventRepository.save(new ProcessedEvent(event.orderId(), "OrderCreatedEvent"));

        log.info("[Kafka] Commande {} passée à COMPLETED avec succès", order.getId());
    }
}
