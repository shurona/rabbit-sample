package com.market.product.service;

import com.market.order.dto.DeliveryMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final RabbitTemplate rabbitTemplate;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${message.queue.payment}")
    private String paymentQueue;

    @Value("${message.queue.err.order}")
    private String errOrderQueue;

    public void reduceProductAmount(DeliveryMessage deliveryMessage) {
        Integer productId = deliveryMessage.getProductId();
        Integer productQuantity = deliveryMessage.getProductQuantity();

        if (productId != 1 || productQuantity == 0) {
            deliveryMessage.setErrorType("PRODUCT ERROR");
            rollbackProduct(deliveryMessage);
            return;
        }

        rabbitTemplate.convertAndSend(paymentQueue, deliveryMessage);

    }

    public void rollbackProduct(DeliveryMessage deliveryMessage) {
        log.info("PRODUCT ROLLBACK");
        rabbitTemplate.convertAndSend(errOrderQueue, deliveryMessage);
    }
}
