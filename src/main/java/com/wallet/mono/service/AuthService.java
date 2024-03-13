package com.wallet.mono.service;

import com.wallet.mono.domain.dto.response.TokenResponse;
import com.wallet.mono.domain.dto.request.UserRequest;
import com.wallet.mono.domain.dto.response.UserResponse;

public interface AuthService {
    UserResponse login(UserRequest userRequest) throws Exception;
    TokenResponse refreshToken(String refreshToken) throws Exception;
}
