package com.coreflow.config.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE = "orders.notifications.queue";
    public static final String EXCHANGE = "orders.notifications.exchange";
    public static final String ROUTING_KEY = "notification.order.created";

    @Bean public Queue queue() { return new Queue(QUEUE, true); }
    @Bean public TopicExchange exchange() { return new TopicExchange(EXCHANGE); }
    @Bean public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }
}
