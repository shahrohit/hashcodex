package com.shahrohit.hashcodex.adapters;

import com.shahrohit.hashcodex.entities.Session;

import java.time.Instant;

public class SessionAdapter {

    public static Session create(Long userId, Instant createdAt, Instant expiresAt) {
        Session session = new Session();
        session.setUser(UserAdapter.toEntity(userId));
        session.setCreatedAt(createdAt);
        session.setExpiresAt(expiresAt);
        return session;
    }
}
