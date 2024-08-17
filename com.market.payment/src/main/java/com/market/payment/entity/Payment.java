package com.market.payment.entity;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Payment {
    private UUID paymentId;
    private String userId;

    private Integer payAmount;

    private String payStatus;
}
