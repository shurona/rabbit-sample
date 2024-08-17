package com.market.order.controller;

import com.market.order.dto.OrderRequestDto;
import com.market.order.entity.Order;
import com.market.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable("orderId") UUID orderId) {

        Order order = orderService.getOrder(orderId);

        return ResponseEntity.ok(order);
    }

    @PostMapping("/order")
    public ResponseEntity<Order> order(@RequestBody OrderRequestDto requestDto) {

        Order order = orderService.createOrder(requestDto);
        return ResponseEntity.ok(order);
    }

}
