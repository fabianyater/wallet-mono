package com.wallet.mono.controller;

import com.wallet.mono.domain.dto.response.TokenResponse;
import com.wallet.mono.domain.dto.request.UserRequest;
import com.wallet.mono.domain.dto.response.UserResponse;
import com.wallet.mono.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@AllArgsConstructor
@RestController
@RequestMapping("auth/")
public class AuthController {
    private final AuthService authService;

    @PostMapping("login")
    public ResponseEntity<UserResponse> saveUser(@RequestBody UserRequest userRequest) throws Exception {
        UserResponse userResponse = authService.login(userRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PostMapping("refresh-token")
    public ResponseEntity<TokenResponse> refreshToken(@PathParam("refreshToken") String refreshToken) throws Exception {
        TokenResponse newToken = authService.refreshToken(refreshToken);
        return new ResponseEntity<>(newToken, HttpStatus.OK);
    }
}
