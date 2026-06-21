package com.coreflow.order.service;

import com.coreflow.order.controller.dto.CreateOrderRequest;
import com.coreflow.order.controller.dto.OrderResponse;
import com.coreflow.order.domain.Order;
import com.coreflow.order.mapper.OrderMapper;
import com.coreflow.order.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

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

    @Transactional
    public OrderResponse getOrderById(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Order not found"));

        // Business logic for retrieving an order can be added here
        return OrderMapper.toResponse(order);
    }

    @Transactional
    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        return orderRepository.findAll().stream()
                .map(OrderMapper::toResponse)
                .toList();
    }
}
