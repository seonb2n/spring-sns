package com.example.springsns.service;

import com.example.springsns.exception.SnsApplicationException;
import com.example.springsns.fixture.UserEntityFixture;
import com.example.springsns.model.entity.UserEntity;
import com.example.springsns.repository.UserEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserEntityRepository userEntityRepository;

    @DisplayName("회원가입 정상적으로 동작")
    @Test
    public void givenUserNameAndPassword_whenJoin_thenReturnUser() throws Exception {
        String userName = "userName";
        String password = "password";

        //given
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());
        when(userEntityRepository.save(any())).thenReturn(Optional.of(UserEntityFixture.get(userName, password)));

        //when & then
        Assertions.assertDoesNotThrow(() -> userService.join(userName, password));
    }

    @DisplayName("회원가입 시 이미 username 으로 회원가입한 유저가 있는 경우")
    @Test
    public void givenUserNameAndPassword_whenJoinWithExistingUser_thenThrowException() throws Exception {
        String userName = "userName";
        String password = "password";

        //given
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(mock(UserEntity.class)));
        when(userEntityRepository.save(any())).thenReturn(Optional.of(mock(UserEntity.class)));

        //when & then
        Assertions.assertThrows(SnsApplicationException.class, () -> userService.join(userName, password));
    }

    @DisplayName("로그인 정상적으로 동작")
    @Test
    public void givenUserNameAndPassword_whenLogin_thenReturnUser() throws Exception {
        String userName = "userName";
        String password = "password";
        UserEntity fixture = UserEntityFixture.get(userName, password);

        //given
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));

        //when & then
        Assertions.assertDoesNotThrow(() -> userService.login(userName, password));
    }

    @DisplayName("로그인 시 해당 username 으로 회원가입한 유저가 없는 경우")
    @Test
    public void givenUserNameAndPassword_whenLoginFailWithNoUser_thenThrowException() throws Exception {
        String userName = "userName";
        String password = "password";
        UserEntity fixture = UserEntityFixture.get(userName, password);

        //given
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());

        //when & then
        Assertions.assertThrows(SnsApplicationException.class, () -> userService.login(userName, password));
    }

    @DisplayName("로그인 시 비밀번호가 틀린 경우")
    @Test
    public void givenUserNameAndPassword_whenLoginFailWithWrongPassword_thenThrowException() throws Exception {
        String userName = "userName";
        String password = "password";
        String loginPassword = "loginPassword";
        UserEntity fixture = UserEntityFixture.get(userName, password);

        //given
        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));

        //when & then
        Assertions.assertThrows(SnsApplicationException.class, () -> userService.login(userName, loginPassword));
    }
}
