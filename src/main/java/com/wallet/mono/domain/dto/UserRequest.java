package com.wallet.mono.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private String userName;
    private String password;
    private String fullName;
}
