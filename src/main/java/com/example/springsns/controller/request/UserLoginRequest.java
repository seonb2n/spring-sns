package com.example.springsns.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class UserLoginRequest {

    private String userName;
    private String password;

}
