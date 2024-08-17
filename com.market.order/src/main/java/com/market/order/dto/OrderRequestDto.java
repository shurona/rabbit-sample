package com.market.order.dto;

import com.market.order.entity.Order;
import lombok.Data;

import java.util.UUID;

@Data
public class OrderRequestDto {

    private String userId;
    private Integer productId;
    private Integer productQuantity;
    private Integer payAmount;

    public Order toOrder() {
        return Order.builder()
                .orderId(UUID.randomUUID())
                .userId(userId)
                .orderStatus("RECEIPT")
                .build();
    }

    public DeliveryMessage toDeliveryMessage(UUID orderId) {
        return DeliveryMessage.builder()
                .orderId(orderId)
                .userId(userId)
                .productId(productId)
                .productQuantity(productQuantity)
                .payAmount(payAmount)
                .build();
    }
}
