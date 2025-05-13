package org.zerock.study.domain.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.study.domain.user.DTO.SigninRequest;
import org.zerock.study.domain.user.DTO.SignupRequest;
import org.zerock.study.domain.entity.MembersEntity;
import org.zerock.study.domain.repository.MemberRepo;
import org.zerock.study.domain.user.service.AuthService;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class AuthServiceTest {
    @Autowired
    private AuthService authService;

    @Autowired
    private MemberRepo memberRepo;


    @Test
    public void 회원가입_성공() {
        SignupRequest mamber = SignupRequest.builder()
                .email("mamber@gmail.com")
                .password("123456")
                .build();

        authService.signup(mamber);

        assertThat(memberRepo.existsByEmail(mamber.email())).isEqualTo(true);
    }

    @Test
    public void 이메일_중복() {
        SignupRequest mamber = SignupRequest.builder()
                .email("mamber@gmail.com")
                .password("123456")
                .build();
        assertThatThrownBy(() -> authService.signup(mamber)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void 로그인_실패() {
        SigninRequest signinRequest = SigninRequest.builder()
                .email("none@gmail.com")
                .password("123456")
                .build();

        assertThatThrownBy(() -> authService.signin(signinRequest))
                .isInstanceOf(IllegalArgumentException.class);

    }
}
