package com.wallet.mono.service;

import com.wallet.mono.domain.dto.UserRequest;
import com.wallet.mono.domain.dto.UserResponse;
import com.wallet.mono.exception.UserNotFoundException;

public interface AuthService {
    UserResponse login(UserRequest userRequest) throws Exception;
}
