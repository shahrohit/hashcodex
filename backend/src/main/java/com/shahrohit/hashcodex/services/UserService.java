package com.shahrohit.hashcodex.services;

import com.shahrohit.hashcodex.dtos.UserProfile;

/**
 * Service interface to handle logged-in user
 */
public interface UserService {
    UserProfile me(Long userId);
}
