package com.shahrohit.hashcodex.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Configuration class for Redis for connecting to Redis Server and for serializing/deserializing data to/from Redis.
 */
@Configuration
public class RedisConfig {

    /**
     * Creates a RedisConnectionFactory bean, which is the core factory interface to establish connections to a Redis server.
     * <p>Uses Lettuce ({@link LettuceConnectionFactory}), a popular, modern, scalable, non-blocking Redis client (based on Netty).</p>
     * <p>By default, it connects to Redis at {@code localhost:6379},
     * unless overridden by properties (e.g., {@code spring.redis.host},{@code spring.redis.port})
     * </p>
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    /**
     * Defines a customized RedisTemplate<String, Object> bean,
     * which is the central class used to perform Redis operations.
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        // ObjectMapper Configuration
        ObjectMapper mapper = new ObjectMapper();
        mapper.activateDefaultTyping(
            BasicPolymorphicTypeValidator.builder()
                .allowIfSubType("com.shahrohit.hashcodex")
                .build(),
            ObjectMapper.DefaultTyping.NON_FINAL
        );

        // Serializer Setup
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(mapper, Object.class);

        // Redis Key and Value Serialization
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);
        template.afterPropertiesSet();
        return template;
    }
}
