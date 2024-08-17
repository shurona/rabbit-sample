package com.market.payment.service;

import com.market.payment.dto.DeliveryMessage;
import com.market.payment.entity.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class PaymentService {
    private final Map<UUID, Payment> paymentStore = new HashMap<>();
    private final RabbitTemplate rabbitTemplate;

    @Value("${message.queue.err.product}")
    private String queueErrProduct;

    public void createPayment(DeliveryMessage deliveryMessage) {

        if (deliveryMessage.getPayAmount() >= 10000) {
            log.error("Payment amount exceeds limit {}", deliveryMessage.getPayAmount());
            deliveryMessage.setErrorType("PAYMENT_LIMIT_EXCEEDED");
            rollbackPayment(deliveryMessage);
            return;
        }

        Payment payment = Payment.builder()
                .paymentId(UUID.randomUUID())
                .userId(deliveryMessage.userId)
                .payAmount(deliveryMessage.getPayAmount())
                .payStatus("SUCCESS")
                .build();
    }

    public void rollbackPayment(DeliveryMessage deliveryMessage) {
        log.error("Payment Rollback!!");
        deliveryMessage.setErrorType("PAYMENT_LIMIT_EXCEEDED");
        rabbitTemplate.convertAndSend(queueErrProduct, deliveryMessage);
    }
}
