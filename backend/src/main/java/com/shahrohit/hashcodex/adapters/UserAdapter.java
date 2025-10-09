package com.shahrohit.hashcodex.adapters;

import com.shahrohit.hashcodex.dtos.UserIdAndProfile;
import com.shahrohit.hashcodex.dtos.UserProfile;
import com.shahrohit.hashcodex.dtos.requests.CreateUserRequest;
import com.shahrohit.hashcodex.entities.User;
import com.shahrohit.hashcodex.enums.Role;

public class UserAdapter {

    public static User toEntity(CreateUserRequest body, String hashedPassword) {
        User user = new User();
        user.setName(body.name());
        user.setEmail(body.email());
        user.setRole(Role.USER);
        user.setEnabled(true);
        user.setHashedPassword(hashedPassword);
        return user;
    }

    public static User toEntity(Long id) {
        User user = new User();
        user.setId(id);
        return user;
    }

    public static UserIdAndProfile toUserIdProfile(User u) {
        return new UserIdAndProfile(u.getId(), from(u));
    }

    public static UserProfile from(User u) {
        return new UserProfile(u.getPublicId(), u.getName(), u.getEmail(), u.getRole(), u.isEmailVerified(), u.isEnabled());
    }
}
