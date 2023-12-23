package com.wallet.mono.service;

import com.wallet.mono.domain.dto.TokenResponse;
import com.wallet.mono.domain.dto.UserRequest;
import com.wallet.mono.domain.dto.UserResponse;

public interface AuthService {
    UserResponse login(UserRequest userRequest) throws Exception;
    TokenResponse refreshToken(String refreshToken) throws Exception;
}
