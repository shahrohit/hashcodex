package com.shahrohit.hashcodex.services.impl;

import com.shahrohit.hashcodex.dtos.UserProfile;
import com.shahrohit.hashcodex.exceptions.NotFoundException;
import com.shahrohit.hashcodex.globals.ErrorCode;
import com.shahrohit.hashcodex.repositories.UserRepository;
import com.shahrohit.hashcodex.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link UserService} to handle the logged-in user
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    public final UserRepository userRepository;

    /**
     * Find User {@link UserProfile} from the user id
     */
    @Override
    public UserProfile me(Long userId) {
        return userRepository.findUserProfileById(userId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }
}
