package com.shahrohit.hashcodex.configs;

import com.shahrohit.hashcodex.utils.Constants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public TopicExchange requestExchange() {
        return ExchangeBuilder.topicExchange(Constants.RABBITMQ.REQ_EXCHANGE).durable(true).build();
    }

    @Bean
    public TopicExchange resultExchange() {
        return ExchangeBuilder.topicExchange(Constants.RABBITMQ.RES_EXCHANGE).durable(true).build();
    }

    @Bean
    public Queue requestQueue() {
        return QueueBuilder.durable(Constants.RABBITMQ.REQ_QUEUE).build();
    }

    @Bean
    public Queue resultQueue() {
        return QueueBuilder.durable(Constants.RABBITMQ.RES_QUEUE).build();
    }

    @Bean
    public Binding requestBinding() {
        return BindingBuilder.bind(requestQueue()).to(requestExchange()).with(Constants.RABBITMQ.REQ_ROUTING_KEY);
    }

    @Bean
    public Binding resultBinding() {
        return BindingBuilder.bind(resultQueue()).to(resultExchange()).with(Constants.RABBITMQ.RES_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
