package com.market.order.controller;

import com.market.order.dto.DeliveryMessage;
import com.market.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderEndpoint {

    private final OrderService orderService;


    @RabbitListener(queues = "${message.queue.err.order}")
    public void rcvError(DeliveryMessage deliveryMessage) {
        orderService.cancelOrder(deliveryMessage);
        log.error("마지막 오더에 에러 메시지 도달 {}", deliveryMessage);
    }

}
