package com.coreflow.order.service;

import com.coreflow.order.controller.dto.CreateOrderRequest;
import com.coreflow.order.controller.dto.OrderResponse;
import com.coreflow.order.domain.Order;
import com.coreflow.order.mapper.OrderMapper;
import com.coreflow.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest createOrderRequest) {
        Order orderEntity = OrderMapper.toEntity(createOrderRequest);

        Order savedOrder = orderRepository.save(orderEntity);

        // Business logic for creating an order can be added here
        return OrderMapper.toResponse(savedOrder);
    }
}
