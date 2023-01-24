package com.example.springsns.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @DisplayName("회원가입 정상적으로 동작")
    @Test
    public void givenUserNameAndPassword_whenJoin_thenReturnUser() throws Exception {
        String userName = "userName";
        String password = "password";

        //given


        //when & then
        Assertions.assertDoesNotThrow(() -> userService.join(userName, password));
    }

}
