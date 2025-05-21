package org.zerock.study.domain.auth.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.study.domain.auth.DTO.SigninRequest;
import org.zerock.study.domain.auth.DTO.SignupRequest;
import org.zerock.study.domain.auth.entity.MembersEntity;
import org.zerock.study.domain.auth.repository.MemberRepo;
import org.zerock.study.global.jwtToken.JwtTokenDTO;
import org.zerock.study.global.jwtToken.JwtTokenProvider;
import org.zerock.study.global.util.HashUtils;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final MemberRepo memberRepo;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    public JwtTokenDTO signup(SignupRequest signupRequest) {
        if (memberRepo.existsByEmail(signupRequest.email())) {
            throw new IllegalArgumentException("Email already in use");
        }
        // 비밀번호 해쉬화
        String hashedPassword = HashUtils.hashPassword(signupRequest.password());
        MembersEntity membersEntity = MembersEntity.builder()
                .email(signupRequest.email())
                .password(hashedPassword)
                .build();

        memberRepo.save(membersEntity);
        return jwtTokenProvider.createToken(membersEntity);
    }

    // 로그인
    public JwtTokenDTO signin(SigninRequest signinRequest) {
        MembersEntity membersEntity = memberRepo.findMembersByEmail(signinRequest.email());
        if (membersEntity == null) {
            throw new IllegalArgumentException("Email not exist");
        }
        if (!HashUtils.matchPassword(signinRequest.password(), membersEntity.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }
        return jwtTokenProvider.createToken(membersEntity);
    }

    // 회원 탈퇴
    public void unsubscript(String email) {
        MembersEntity membersEntity = memberRepo.findMembersByEmail(email);
        if (membersEntity == null) {
            throw new IllegalArgumentException("Email not exist");
        }
        memberRepo.delete(membersEntity);
    }

    // 새로운 토큰 얻기
    public JwtTokenDTO getJwtToken(String refreshToken) {
        return jwtTokenProvider.getTokenWithRefresh(refreshToken);
    }

}
