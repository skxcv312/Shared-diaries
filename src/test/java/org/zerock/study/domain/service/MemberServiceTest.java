package org.zerock.study.domain.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.study.domain.DTO.SigninRequest;
import org.zerock.study.domain.DTO.SignupRequest;
import org.zerock.study.domain.entity.Members;
import org.zerock.study.domain.repository.MemberRepo;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MemberServiceTest {
    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepo memberRepo;

    @BeforeAll
    void setUp() {
        memberRepo.deleteAll();
    }

    @Test
    public void 회원가입_테스트(){
        SignupRequest mamber = SignupRequest.builder()
                .email("mamber@gmail.com")
                .name("mamber")
                .password("123456")
                .build();

        memberService.signup(mamber);

        assertThat(memberRepo.existsByEmail(mamber.email())).isEqualTo(true);
    }

    @Test
    public void 로그인_테스트(){
        SigninRequest signinRequest = SigninRequest.builder()
                .email("mamber@gmail.com")
                .password("123456")
                .build();
        Members members = memberService.signin(signinRequest);

        assertThat(members.getName()).isEqualTo("mamber");

    }

    @Test
    public void 로그인_실패(){
        SigninRequest signinRequest = SigninRequest.builder()
                .email("none@gmail.com")
                .password("123456")
                .build();

        assertThatThrownBy(() -> memberService.signin(signinRequest))
                .isInstanceOf(IllegalArgumentException.class);

    }
}
