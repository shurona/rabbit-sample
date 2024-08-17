package com.market.order.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderApplicationQueueConfig {

    @Value("${message.exchange}")
    private String exchange;

    @Value("${message.queue.product}")
    private String queueProduct;

    @Value("${message.queue.payment}")
    private String queuePayment;


    @Value("${message.err.exchange}")
    private String exchangeErr;

    @Value("${message.queue.err.order}")
    private String queueErrOrder;

    @Value("${message.queue.err.product}")
    private String queueErrProduct;

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Queue queueProduct() {
        return new Queue(queueProduct);
    }

    @Bean
    public Queue queuePayment() {
        return new Queue(queuePayment);
    }

    // order -> product
    @Bean
    public Binding bindingProduct() {
        return  BindingBuilder.bind(queueProduct()).to(exchange()).with(queueProduct);
    }

    // product -> payment
    @Bean
    public Binding bindingPayment() {
        return BindingBuilder.bind(queuePayment()).to(exchange()).with(queuePayment);
    }

    @Bean
    public TopicExchange errorExchange() {
        return new TopicExchange(exchangeErr);
    }

    @Bean
    public Queue queueErrProduct() {
        return new Queue(queueErrProduct);
    }

    @Bean
    public Queue queueErrOrder() {
        return new Queue(queueErrOrder);
    }

    @Bean
    public Binding bindingErrorProduct() {
        return BindingBuilder.bind(queueErrProduct()).to(errorExchange()).with(queueErrProduct);
    }

    @Bean
    public Binding bindingErrorOrder() {
        return BindingBuilder.bind(queueErrOrder()).to(errorExchange()).with(queueErrOrder);
    }
}
