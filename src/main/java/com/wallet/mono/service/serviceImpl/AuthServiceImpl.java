package com.wallet.mono.service.serviceImpl;

import com.wallet.mono.configuration.jwt.JwtUtils;
import com.wallet.mono.domain.dto.AccountResponse;
import com.wallet.mono.domain.dto.TokenResponse;
import com.wallet.mono.domain.dto.UserRequest;
import com.wallet.mono.domain.dto.UserResponse;
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

        String token = jwtUtils.generateToken(userRequest.getUserName());
        String refreshToken = jwtUtils.generateRefrestToken(userRequest.getUserName());

        userResponse.setUserName(userRequest.getUserName());
        userResponse.setFullName(userRequest.getFullName());
        userResponse.setJwt(token);
        userResponse.setRefreshToken(refreshToken);
        userResponse.setExpirationDate(jwtUtils.extractExpirationInMillis(token));
        userResponse.setUserId(userService.getUserDetails(userRequest.getUserName()).getUserId());
        List<AccountResponse> accounts = accountService.getAccountsByUserId(userResponse.getUserId());

        if (!accounts.isEmpty()) {
            accounts.stream().findFirst().ifPresent(account -> userResponse.setAccountId(account.getAccountId()));
        }

        return userResponse;
    }

    @Override
    public TokenResponse refreshToken(String refreshToken) throws Exception {
        String username = jwtUtils.extractUserName(refreshToken);

        if (jwtUtils.validateRefreshToken(refreshToken, username)) {
            String newToken = jwtUtils.generateToken(username);
            String newRefreshToken = jwtUtils.generateRefrestToken(username);
            long expirationDate = jwtUtils.extractExpirationInMillis(newToken);
            return TokenResponse.builder()
                    .refreshToken(newRefreshToken)
                    .jwt(newToken)
                    .expirationDate(expirationDate)
                    .build();
        }
        throw new Exception("Algo salió mal");
    }
}
