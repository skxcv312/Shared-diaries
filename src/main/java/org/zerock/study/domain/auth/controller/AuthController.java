package org.zerock.study.domain.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.study.domain.auth.DTO.RefreshRequest;
import org.zerock.study.domain.auth.DTO.SigninRequest;
import org.zerock.study.domain.auth.DTO.SignupRequest;
import org.zerock.study.domain.auth.service.AuthService;
import org.zerock.study.domain.auth.DTO.JwtTokenDTO;
import org.zerock.study.global.jwtToken.JwtTokenProvider;

@RestController()
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    // 로그인
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody SigninRequest signinRequest) {
        JwtTokenDTO jwtTokenDTO = authService.signin(signinRequest);
        HttpHeaders headers = jwtTokenProvider.setTokenToHeader(jwtTokenDTO);
        return ResponseEntity.ok()
                .headers(headers)
                .build();
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignupRequest signupRequest) {

        JwtTokenDTO jwtTokenDTO = authService.signup(signupRequest);
        HttpHeaders headers = jwtTokenProvider.setTokenToHeader(jwtTokenDTO);
        return ResponseEntity.ok()
                .headers(headers)
                .build();
    }

    // 로그아웃
    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        return null;
    }

    // 리프레쉬토큰 발급
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshRequest refreshRequest) {
        JwtTokenDTO jwtTokenDTO = jwtTokenProvider.getTokenWithRefresh(refreshRequest.refreshToken());
        HttpHeaders headers = jwtTokenProvider.setTokenToHeader(jwtTokenDTO);
        return ResponseEntity.ok()
                .headers(headers)
                .build();
    }


}
