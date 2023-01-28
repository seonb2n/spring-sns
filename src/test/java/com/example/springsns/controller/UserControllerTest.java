package com.example.springsns.controller;

import com.example.springsns.controller.request.UserJoinRequest;
import com.example.springsns.controller.request.UserLoginRequest;
import com.example.springsns.exception.SnsApplicationException;
import com.example.springsns.model.User;
import com.example.springsns.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @DisplayName("회원가입")
    @Test
    public void givenUserNameAndPassword_whenJoin_thenReturnUser() throws Exception {
        //given
        String userName = "userName";
        String password = "password";

        //when
        when(userService.join("", "")).thenReturn(mock(User.class));
        mvc.perform(post("/api/v1/users/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new UserJoinRequest(userName, password)))
        ).andDo(print())
                .andExpect(status().isOk());

        //then
    }

    @DisplayName("회원가입 - 이미 존재하는 아이디 실패")
    @Test
    public void givenUserNameAndPassword_whenJoinWithExistingEmail_thenReturnUser() throws Exception {
        //given
        String userName = "userName";
        String password = "password";

        //when
        when(userService.join("", "")).thenThrow(new SnsApplicationException());

        mvc.perform(post("/api/v1/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserJoinRequest(userName, password)))
                ).andDo(print())
                .andExpect(status().isConflict());

        //then
    }

    @DisplayName("로그인")
    @Test
    public void givenUserNameAndPassword_whenLogin_thenReturnUser() throws Exception {
        //given
        String userName = "userName";
        String password = "password";

        //when
        when(userService.login(userName, password)).thenReturn("test_token");
        mvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(userName, password)))
                ).andDo(print())
                .andExpect(status().isOk());

        //then
    }

    @DisplayName("로그인 - 가입 안된 userName 으로 시도 시 실패")
    @Test
    public void givenUserNameAndPassword_whenLoginWithWrongUsername_thenReturnUser() throws Exception {
        //given
        String userName = "userName";
        String password = "password";

        //when
        when(userService.login(userName, password)).thenThrow(new SnsApplicationException());
        mvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(userName, password)))
                ).andDo(print())
                .andExpect(status().isNotFound());

        //then
    }

    @DisplayName("로그인 - 틀린 password로 시도 시 실패")
    @Test
    public void givenUserNameAndPassword_whenLoginWithWrongPassword_thenReturnUser() throws Exception {
        //given
        String userName = "userName";
        String password = "password";

        //when
        when(userService.login(userName, password)).thenThrow(new SnsApplicationException());
        mvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(userName, password)))
                ).andDo(print())
                .andExpect(status().isUnauthorized());

        //then
    }
}
