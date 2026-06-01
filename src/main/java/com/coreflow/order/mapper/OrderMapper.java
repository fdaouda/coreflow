package com.coreflow.order.mapper;

import com.coreflow.order.controller.dto.CreateOrderRequest;
import com.coreflow.order.controller.dto.OrderResponse;
import com.coreflow.order.domain.Order;
import com.coreflow.order.domain.OrderStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;


public class OrderMapper {

    //Pour transformer le dto en entité pour la DB
    public static Order toEntity(CreateOrderRequest request) {
        Order order = new Order();

        BigDecimal totalAmount = request.items().stream()
                .map( item -> item.price().multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(Instant.now());
        order.setAmount(totalAmount);
        order.setCustomerId(request.customerId().toString());

        return order;
    }

    //Pour transformer l'entité en dto pour la réponse
    public static OrderResponse toResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                UUID.fromString(order.getCustomerId()),
                order.getStatus(),
                order.getAmount(),
                order.getCreatedAt());
    }
}
