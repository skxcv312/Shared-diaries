package org.zerock.study.domain.profile.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.study.domain.auth.entity.MembersEntity;
import org.zerock.study.domain.profile.service.ProfileService;
import org.zerock.study.global.jwtToken.JwtTokenProvider.MemberTokenInfo;
import org.zerock.study.global.jwtToken.MemberContext;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class ProfileController { // 유저 정보 조회
    private final ProfileService profileService;

    public record ProfileResponse(
            String email,
            String createdAt
    ) {
    }

    @GetMapping("/me")
    public ResponseEntity<?> profile() {
        MemberTokenInfo memberTokenInfo = MemberContext.get();

        String email = memberTokenInfo.email();
        String creatAt = profileService.getMemberCreatAt(memberTokenInfo.userId());

        ProfileResponse profileResponse = new ProfileResponse(email, creatAt);
        return ResponseEntity.ok(profileResponse);
    }
}
