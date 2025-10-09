package com.shahrohit.hashcodex.services;


import com.shahrohit.hashcodex.dtos.UserIdAndProfile;
import com.shahrohit.hashcodex.dtos.requests.*;

/**
 * Service interface to handle the User's authentication.
 */
public interface AuthUserService {
    void register(CreateUserRequest body);
    UserIdAndProfile login(EmailLoginRequest body);
    void verifyEmail(VerifyEmailRequest body);
    void forgetPassword(ForgetPasswordRequest body);
    void resetResetPassword(ResetPasswordRequest body);
}
