package com.shahrohit.hashcodex.configs;

import com.shahrohit.hashcodex.utils.Constants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    /**
     * Creating Direct Request Exhange
     * @return {@link DirectExchange}
     */
    @Bean
    public DirectExchange requestExchange() {
        return ExchangeBuilder.directExchange(Constants.RABBITMQ.REQ_EXCHANGE)
            .durable(true)
            .build();
    }

    /**
     * Creating Direct Response Exhange
     * @return {@link DirectExchange}
     */
    @Bean
    public DirectExchange resultExchange() {
        return ExchangeBuilder.directExchange(Constants.RABBITMQ.RES_EXCHANGE)
            .durable(true)
            .build();
    }

    /**
     * Creating Request Queue
     * @return {@link Queue}
     */
    @Bean
    public Queue requestQueue() {
        return QueueBuilder.durable(Constants.RABBITMQ.REQ_QUEUE).build();
    }

    /**
     * Creating Response Qeueue
     * @return {@link Queue}
     */
    @Bean
    public Queue resultQueue() {
        return QueueBuilder.durable(Constants.RABBITMQ.RES_QUEUE).build();
    }

    /**
     * Binding Request Queue to Request Exchange with the Routing Key
     * @return {@link Binding}
     */
    @Bean
    public Binding requestBinding() {
        return BindingBuilder.bind(requestQueue())
            .to(requestExchange())
            .with(Constants.RABBITMQ.REQ_ROUTING_KEY);
    }

    /**
     * Binding Result Queue to Result  Exchange with the Routing Key
     * @return {@link Binding}
     */
    @Bean
    public Binding resultBinding() {
        return BindingBuilder.bind(resultQueue())
            .to(resultExchange())
            .with(Constants.RABBITMQ.RES_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
