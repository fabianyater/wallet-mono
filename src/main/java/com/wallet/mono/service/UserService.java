package com.wallet.mono.service;

import com.wallet.mono.domain.dto.UserRequest;
import com.wallet.mono.domain.model.User;
import com.wallet.mono.exception.UserNotFoundException;

public interface UserService {
    void save(UserRequest userRequest) throws Exception;
    User getUserDetails(String userName) throws UserNotFoundException;
    boolean existsUser(Integer id) throws UserNotFoundException;
}
