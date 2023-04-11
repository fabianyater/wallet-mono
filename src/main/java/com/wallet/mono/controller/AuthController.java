package com.wallet.mono.controller;

import com.wallet.mono.domain.dto.UserRequest;
import com.wallet.mono.domain.dto.UserResponse;
import com.wallet.mono.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
