package com.wallet.mono.service.serviceImpl;

import com.wallet.mono.configuration.jwt.JwtUtils;
import com.wallet.mono.domain.dto.AccountResponse;
import com.wallet.mono.domain.dto.UserRequest;
import com.wallet.mono.domain.dto.UserResponse;
import com.wallet.mono.domain.model.Account;
import com.wallet.mono.exception.UserNotFoundException;
import com.wallet.mono.service.AccountService;
import com.wallet.mono.service.AuthService;
import com.wallet.mono.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final AccountService accountService;


    @Override
    public UserResponse login(UserRequest userRequest) throws Exception {
        UserResponse userResponse = new UserResponse();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userRequest.getUserName(),
                        userRequest.getPassword()
                )
        );

        userResponse.setUserName(userRequest.getUserName());
        userResponse.setFullName(userRequest.getFullName());
        userResponse.setJwt(jwtUtils.generateToken(userRequest.getUserName()));
        userResponse.setUserId(userService.getUserDetails(userRequest.getUserName()).getUserId());
        List<AccountResponse> accounts = accountService.getAccountsByUserId(userResponse.getUserId());
        userResponse.setAccountId(Integer.parseInt(accounts.stream().findFirst().get().getAccountId()));

        return userResponse;
    }
}
