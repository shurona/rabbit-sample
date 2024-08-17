package com.market.product.controller;

import com.market.order.dto.DeliveryMessage;
import com.market.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductEndpoint {

    private final ProductService productService;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @RabbitListener(queues = "${message.queue.product}")
    public void receiveMessage(DeliveryMessage deliveryMessage) {

        log.info("PRODUCT RECEIVER {}", deliveryMessage);
        productService.reduceProductAmount(deliveryMessage);

    }

    @RabbitListener(queues = "${message.queue.err.product}")
    public void rcvErrorMessage(DeliveryMessage deliveryMessage) {
        log.info("ERR RECEIVE {}", productService);
        productService.rollbackProduct(deliveryMessage);
    }

}
