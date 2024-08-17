package com.market.payment.controller;

import com.market.payment.dto.DeliveryMessage;
import com.market.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentEndpoint {

    private final PaymentService paymentService;

    @RabbitListener(queues = "${message.queue.payment}")
    public void rcvMessage(DeliveryMessage deliveryMessage) {
        log.info("PAYMENT MESSAGE: {}", deliveryMessage);
        paymentService.createPayment(deliveryMessage);
    }
}
