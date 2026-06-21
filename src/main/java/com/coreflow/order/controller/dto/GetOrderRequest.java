package com.coreflow.order.controller.dto;

import java.util.UUID;

public record GetOrderRequest(
        UUID orderId
) { }
