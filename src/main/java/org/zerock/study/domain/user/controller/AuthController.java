package org.zerock.study.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.study.domain.user.DTO.RefreshRequest;
import org.zerock.study.domain.user.DTO.SigninRequest;
import org.zerock.study.domain.user.DTO.SignupRequest;
import org.zerock.study.domain.user.service.AuthService;
import org.zerock.study.global.jwtToken.JwtTokenDTO;

@RestController()
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    // 로그인
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody SigninRequest signinRequest) {
        JwtTokenDTO jwtTokenDTO = authService.signin(signinRequest);
        return ResponseEntity.ok(jwtTokenDTO);
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignupRequest signupRequest) {

        JwtTokenDTO jwtTokenDTO = authService.signup(signupRequest);
        return ResponseEntity.ok(jwtTokenDTO);
    }

    // 로그아웃
    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        return null;
    }

    // 리프레쉬토큰 발급
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshRequest refreshRequest) {
        JwtTokenDTO jwtTokenDTO = authService.getJwtToken(refreshRequest.refreshToken());
        return ResponseEntity.ok(jwtTokenDTO);
    }


}
