package com.example.springsns.service;

import com.example.springsns.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    public User join(String userName, String password) {
        return new User();
    }

    public String login() {
        return "";
    }
}
