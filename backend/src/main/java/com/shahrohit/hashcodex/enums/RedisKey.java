package com.shahrohit.hashcodex.enums;

import lombok.Getter;

public enum RedisKey {
    VERIFY("verify_"), PASSWORD("password_");

    @Getter
    private final String key;

    RedisKey(String key) {
        this.key = key;
    }

    public String generate(String identifier) {
        return key + identifier;
    }
}
