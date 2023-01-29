package com.example.springsns.service;

import com.example.springsns.exception.SnsApplicationException;
import com.example.springsns.model.User;
import com.example.springsns.model.entity.UserEntity;
import com.example.springsns.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserEntityRepository userEntityRepository;

    public User join(String userName, String password) {
        userEntityRepository.findByUserName(userName).ifPresent(it -> {
            throw new SnsApplicationException();
        });
        var userEntity = userEntityRepository.save(UserEntity.of(userName, password));
        return User.fromEntity(userEntity);
    }

    public String login(String userName, String password) {
        // 회원가입 여부 체크
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(SnsApplicationException::new);

        // 비밀번호 체크
        if (userEntity.getPassword().equals(password)) {
            throw new SnsApplicationException();
        }

        // 토큰 생성

        return "";
    }
}
