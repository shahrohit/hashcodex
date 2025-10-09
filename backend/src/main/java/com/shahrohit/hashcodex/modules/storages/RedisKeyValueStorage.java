package com.shahrohit.hashcodex.modules.storages;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * Redis-based implementation of the {@link KeyValueStorage} interface.
 */
@Component
@RequiredArgsConstructor
public class RedisKeyValueStorage implements KeyValueStorage {
    private final RedisTemplate<String, Object> redisTemplate;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> void set(String key, T value, Duration ttl) {
        redisTemplate.opsForValue().set(key, value, ttl);
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        try{
            Object value = redisTemplate.opsForValue().get(key);
            if (value == null) return null;
            return objectMapper.convertValue(value, clazz);
        } catch (Exception e){
            return null;
        }
    }

    public <T> T getAndDelete(String key, Class<T> clazz) {
        try{
            Object value = redisTemplate.opsForValue().getAndDelete(key);
            if(value == null) return null;
            return objectMapper.convertValue(value, clazz);
        } catch (Exception e){
            return null;
        }
    }
}
