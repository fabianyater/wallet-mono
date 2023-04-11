package com.wallet.mono.controller;

import com.wallet.mono.domain.dto.AccountRequest;
import com.wallet.mono.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("accounts/")
public class AccountController {
    private final AccountService accountService;

    @PostMapping()
    public ResponseEntity<Void> saveUser(@RequestBody AccountRequest accountRequest) throws Exception {
        accountService.saveAccount(accountRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
