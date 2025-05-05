package org.zerock.study.domain.controller;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.study.domain.DTO.SigninRequest;
import org.zerock.study.domain.DTO.SignupRequest;
import org.zerock.study.domain.entity.Members;
import org.zerock.study.domain.service.MemberService;

@RestController()
@RequestMapping("/auth")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    // 로그인
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody SigninRequest signinRequest) {
        Members members = memberService.signin(signinRequest);
        return ResponseEntity.ok(members);
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignupRequest signupRequest) {

        Members members = memberService.signup(signupRequest);
        return ResponseEntity.ok(members);
    }

    // 로그아웃
    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        return null;
    }

    // 리프레쉬토큰 발급
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh() {
        return null;
    }


}
