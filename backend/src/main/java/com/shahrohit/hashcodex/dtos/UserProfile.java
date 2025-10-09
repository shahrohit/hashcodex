package com.shahrohit.hashcodex.dtos;

import com.shahrohit.hashcodex.enums.Role;

import java.util.UUID;

/**
 * DTO representing User basic information
 */
public record UserProfile(
    UUID publicId,
    String name,
    String email,
    Role role,
    boolean emailVerified,
    boolean enabled
){}
