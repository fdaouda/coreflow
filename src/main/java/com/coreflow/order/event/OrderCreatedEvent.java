package com.coreflow.order.event;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderCreatedEvent(
    UUID orderId,
    UUID costumerId,
    BigDecimal totalAmount
) {}
