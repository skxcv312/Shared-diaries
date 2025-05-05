package org.zerock.study.domain.user.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.study.domain.user.DTO.SigninRequest;
import org.zerock.study.domain.user.DTO.SignupRequest;
import org.zerock.study.domain.entity.Members;
import org.zerock.study.domain.repository.MemberRepo;
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
        Members members = Members.builder()
                .email(signupRequest.email())
                .password(hashedPassword)
                .build();

        memberRepo.save(members);
        return jwtTokenProvider.createToken(members);
    }

    // 로그인
    public JwtTokenDTO signin(SigninRequest signinRequest) {
        Members members = memberRepo.findMembersByEmail(signinRequest.email());
        if (members == null) {
            throw new IllegalArgumentException("Email not exist");
        }
        if (!HashUtils.matchPassword(signinRequest.password(), members.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }
        return jwtTokenProvider.createToken(members);
    }

    // 회원 탈퇴
    public void unsubscript(String email) {
        Members members = memberRepo.findMembersByEmail(email);
        if (members == null) {
            throw new IllegalArgumentException("Email not exist");
        }
        memberRepo.delete(members);
    }

    // 새로운 토큰 얻기
    public JwtTokenDTO getJwtToken(String refreshToken) {
        return jwtTokenProvider.getTokenWithRefresh(refreshToken);
    }

}
