package org.zerock.study.domain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.study.domain.DTO.SigninRequest;
import org.zerock.study.domain.DTO.SignupRequest;

@RestController()
@RequestMapping("/auth")
@RequiredArgsConstructor
public class MemberController {

    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody SigninRequest signinRequest) {
        return null;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignupRequest signupRequest) {
        return null;
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        return null;
    }


}
