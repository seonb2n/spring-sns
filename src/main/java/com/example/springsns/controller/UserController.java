package com.example.springsns.controller;

import com.example.springsns.controller.request.UserJoinRequest;
import com.example.springsns.controller.response.UserJoinResponse;
import com.example.springsns.model.User;
import com.example.springsns.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity join(@RequestBody UserJoinRequest request) {
        // join
        User user = userService.join(request.getUserName(), request.getPassword());
        UserJoinResponse response = UserJoinResponse.fromUser(user);
        return ResponseEntity.ok(response);
    }


    public void login() {

    }

}
