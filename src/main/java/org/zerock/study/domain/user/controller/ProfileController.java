package org.zerock.study.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.study.domain.entity.Members;
import org.zerock.study.global.jwtToken.MemberContext;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class ProfileController { // 유저 정보 조회

    @GetMapping("/me")
    public ResponseEntity<?> profile() {
        Members members = MemberContext.get();
        return ResponseEntity.ok(members);
    }
}
