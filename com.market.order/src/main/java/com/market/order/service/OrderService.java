package com.market.order.service;

import com.market.order.dto.DeliveryMessage;
import com.market.order.dto.OrderRequestDto;
import com.market.order.entity.Order;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final Map<UUID, Order> orderStore = new HashMap<>();

    private final RabbitTemplate rabbitTemplate;

    @Value("${message.queue.product}")
    private String productQueue;

    public Order getOrder(UUID orderId) {
        return orderStore.getOrDefault(orderId, null);
    }

    public Order createOrder(OrderRequestDto requestDto) {
        Order order = requestDto.toOrder();
        orderStore.put(order.getOrderId(), order);

        DeliveryMessage deliveryMessage = requestDto.toDeliveryMessage(order.getOrderId());
        rabbitTemplate.convertAndSend(productQueue, deliveryMessage);

        return order;
    }

    public void cancelOrder(DeliveryMessage deliveryMessage) {
        Order order = orderStore.get(deliveryMessage.getOrderId());
        order.cancelOrder(deliveryMessage.getErrorType());
        orderStore.put(deliveryMessage.getOrderId(), order);
    }
}
