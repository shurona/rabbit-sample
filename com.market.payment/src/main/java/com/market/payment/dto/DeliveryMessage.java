package com.market.payment.dto;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryMessage {
    private UUID orderId;
    private UUID payment;

    public String userId;
    private Integer productId;
    private Integer productQuantity;
    private Integer payAmount;

    private String errorType;

}
