package com.shahrohit.hashcodex.services.impl;

import com.shahrohit.hashcodex.adapters.UserAdapter;
import com.shahrohit.hashcodex.modules.notifications.NotificationTemplate;
import com.shahrohit.hashcodex.modules.storages.RedisKeyValueStorage;
import com.shahrohit.hashcodex.dtos.PublicIdAndName;
import com.shahrohit.hashcodex.dtos.UserIdAndProfile;
import com.shahrohit.hashcodex.dtos.requests.*;
import com.shahrohit.hashcodex.entities.User;
import com.shahrohit.hashcodex.enums.RedisKey;
import com.shahrohit.hashcodex.exceptions.AlreadyExistsException;
import com.shahrohit.hashcodex.exceptions.BadRequestException;
import com.shahrohit.hashcodex.exceptions.ForbiddenException;
import com.shahrohit.hashcodex.exceptions.NotFoundException;
import com.shahrohit.hashcodex.globals.ErrorCode;
import com.shahrohit.hashcodex.properties.FrontendProperties;
import com.shahrohit.hashcodex.repositories.UserRepository;
import com.shahrohit.hashcodex.services.AuthUserService;
import com.shahrohit.hashcodex.utils.Constants;
import com.shahrohit.hashcodex.utils.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Implementation of {@link AuthUserService} to handle User authentication
 */
@Service
@RequiredArgsConstructor
public class AuthUserServiceImpl implements AuthUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher publisher;
    private final RedisKeyValueStorage redisStorage;
    private final FrontendProperties frontendProperties;

    @Override
    public void register(CreateUserRequest body) {
        if (userRepository.existsByEmail(body.email()))
            throw new AlreadyExistsException(ErrorCode.EMAIL_ALREADY_EXISTS);

        User user = userRepository.save(UserAdapter.toEntity(body, passwordEncoder.encode(body.password())));

        String url = generateURLAndSaveToRedis(RedisKey.VERIFY, user.getPublicId(), Constants.Auth.VERIFY_ACCOUNT);
        publisher.publishEvent(NotificationTemplate.verifyEmail(user.getName(), body.email(), url));
    }

    @Override
    @Transactional
    public void verifyEmail(VerifyEmailRequest body) {
        String token = extractTokenFromRedis(RedisKey.VERIFY, body.publicId());
        if (!Objects.equals(token, body.token())) throw new NotFoundException(ErrorCode.LINK_EXPIRED);

        int updateCount = userRepository.updateEmailVerifiedByPublicId(body.publicId(), Instant.now());
        if (updateCount == 0) throw new NotFoundException(ErrorCode.USER_NOT_FOUND);
    }

    @Override
    public UserIdAndProfile login(EmailLoginRequest body) {
        User user = userRepository.findByEmail(body.email())
            .orElseThrow(() -> new ForbiddenException(ErrorCode.CREDENTIALS_INVALID));

        boolean passwordMatched = passwordEncoder.matches(body.password(), user.getHashedPassword());
        if (!passwordMatched) throw new ForbiddenException(ErrorCode.CREDENTIALS_INVALID);

        if (user.isEmailVerified()) return UserAdapter.toUserIdProfile(user);

        String url = generateURLAndSaveToRedis(RedisKey.VERIFY, user.getPublicId(), Constants.Auth.VERIFY_ACCOUNT);
        publisher.publishEvent(NotificationTemplate.verifyEmail(user.getName(), body.email(), url));
        return null;
    }

    @Override
    public void forgetPassword(ForgetPasswordRequest body) {
        PublicIdAndName user = userRepository.findPublicIdAndNameByEmail(body.email())
            .orElseThrow(() -> new NotFoundException(ErrorCode.EMAIL_NOT_FOUND));

        String url = generateURLAndSaveToRedis(RedisKey.PASSWORD, user.publicId(), Constants.Auth.RESET_PASSWORD);
        publisher.publishEvent(NotificationTemplate.resetPassword(user.name(), body.email(), url));
    }

    @Override
    @Transactional
    public void resetResetPassword(ResetPasswordRequest body) {
        if (!Objects.equals(body.password(), body.confirmPassword())) throw new BadRequestException(ErrorCode.PASSWORD_NOT_MATCH);

        String token = extractTokenFromRedis(RedisKey.PASSWORD, body.publicId());
        if (!Objects.equals(token, body.token())) throw new NotFoundException(ErrorCode.LINK_EXPIRED);

        int updateCount = userRepository.updatePasswordByPublicId(body.publicId(), passwordEncoder.encode(body.password()), Instant.now());
        if (updateCount == 0) throw new NotFoundException(ErrorCode.USER_NOT_FOUND);
    }

    /**
     * Helper Method to Generate Secure Token and save to Redis
     * @param key - Redis Key
     * @param publicId - public ID of the user
     * @param type - Authentication type - Verify Account or Reset Password
     * @return URL which will be sent back to the frontend
     */
    private String generateURLAndSaveToRedis(RedisKey key, UUID publicId, String type) {
        String token = Helper.generateSecureToken();
        redisStorage.set(key.generate(publicId.toString()), token, Duration.ofMinutes(15));
        return String.format("%s/auth/%s?token=%s&publicId=%s", frontendProperties.url(), type, token, publicId);
    }

    /**
     * Helper method to extract the token from redis
     * @param key - Redis Key
     * @param publicId - Public ID of the user
     * @return value store again the redist key
     */
    private String extractTokenFromRedis(RedisKey key, UUID publicId) {
        return redisStorage.getAndDelete(key.generate(publicId.toString()), String.class);
    }
}
