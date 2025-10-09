package com.shahrohit.hashcodex.services;

import com.shahrohit.hashcodex.dtos.UserProfile;

public interface UserService {
    UserProfile me(Long userId);
}
