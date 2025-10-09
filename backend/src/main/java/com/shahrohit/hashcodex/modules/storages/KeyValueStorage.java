package com.shahrohit.hashcodex.modules.storages;

import java.time.Duration;

/**
 * Interface representing a generic key-value storage system with Time-To-Live (TTL) support.
 * <p>
 * Intended for caching or temporary data storage,
 * typically backed by systems like Redis.
 * </p>
 */
public interface KeyValueStorage {
    <T> void set(String key, T value, Duration ttl);
    <T> T get(String key, Class<T> clazz);
    <T> T getAndDelete(String key, Class<T> clazz);
}
