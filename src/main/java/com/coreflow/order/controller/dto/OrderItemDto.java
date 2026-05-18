package com.coreflow.order.controller.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemDto(
        UUID productId,
        int quantity,
        BigDecimal price
) {}
