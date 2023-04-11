package com.wallet.mono.controller;

import com.wallet.mono.domain.dto.UserRequest;
import com.wallet.mono.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("users/")
public class UserController {
    private final UserService userService;

    @PostMapping("signup")
    public ResponseEntity<Void> saveUser(@RequestBody UserRequest userRequest) throws Exception {
        userService.save(userRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
