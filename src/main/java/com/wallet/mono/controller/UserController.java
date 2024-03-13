package com.wallet.mono.controller;

import com.wallet.mono.domain.dto.request.UserRequest;
import com.wallet.mono.service.UserService;
import com.wallet.mono.utils.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("users/")
public class UserController {
    private final UserService userService;

    @PostMapping("signup")
    public ResponseEntity<ApiResponse<String>> saveUser(@RequestBody UserRequest userRequest) throws Exception {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        userService.save(userRequest);

        apiResponse.setStatus(HttpStatus.CREATED.value());
        apiResponse.setMessage("Account created successfully");
        apiResponse.setData(null);
        apiResponse.setPagination(null);

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
}
