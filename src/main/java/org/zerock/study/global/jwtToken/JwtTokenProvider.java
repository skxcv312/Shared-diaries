package org.zerock.study.global.jwtToken;

import io.jsonwebtoken.*;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;


import javax.crypto.SecretKey;
import java.util.Date;
import org.zerock.study.global.config.JwtConfig;
import org.zerock.study.domain.auth.entity.MembersEntity;
import org.zerock.study.domain.auth.repository.MemberRepo;


@Slf4j
@Configuration
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final MemberRepo memberRepo;
    private final JwtConfig jwtConfig;


    @Builder
    public record MemberTokenInfo(
            Long userId,
            String email,
            Role role
    ) {
    }


    private String setAccessToken(MembersEntity user) {
        Date now = new Date();

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("id", user.getId().toString())
                .claim("role", user.getRole().name())
                .issuedAt(now) // 토큰 발행 시간 정보
                .expiration(new Date(now.getTime() + jwtConfig.getAccessTokenValidTime())) // 토큰 유효시각 설정
                .signWith(jwtConfig.getSecretKey())  // 암호화 알고리즘과, secret 값
                .compact();
    }


    private String setRefreshToken(MembersEntity user) {
        Date now = new Date();
        return Jwts.builder()
                .subject(user.getEmail())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + jwtConfig.getRefreshTokenValidTime()))
                .signWith(jwtConfig.getSecretKey())
                .compact();
    }

    // 토큰 생성
    public JwtTokenDTO createToken(MembersEntity user) {  // userPK = email

        String accessToken = setAccessToken(user);
        String refreshToken = setRefreshToken(user);
        return new JwtTokenDTO(accessToken, refreshToken);
    }

    // 리프레시토큰으로 엑세스토큰얻기
    public JwtTokenDTO getTokenWithRefresh(String refreshToken) {
        String userEmail = validateToken(refreshToken).getSubject();
        MembersEntity user = memberRepo.findMembersByEmail(userEmail);
        return createToken(user);
    }

    // 인증 정보 조회 가져오기
    public MemberTokenInfo getAuthentication(String token) {
        Claims userClaims = validateToken(token);
        String email = userClaims.getSubject();
        String roleString = userClaims.get("role", String.class);
        Role role = roleString != null ? Role.valueOf(roleString) : null;
        Long id = userClaims.get("id", Long.class);
        return MemberTokenInfo.builder()
                .email(email)
                .role(role)
                .userId(id)
                .build();
    }


    // 토큰 유효성, 만료일자 확인
    public Claims validateToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) jwtConfig.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public ResponseCookie setTokenToCookie(String tokenKey, String tokenValue, long maxAge) {
        return ResponseCookie.from(tokenKey, tokenValue)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(maxAge)
                .sameSite("Strict")
                .build();
    }

    public HttpHeaders setTokenToHeader(JwtTokenDTO token) {
        String accessToken = token.accessToken();
        String refreshToken = token.refreshToken();

        // Refresh Token 쿠키로 설정
        ResponseCookie cookie = setTokenToCookie(
                "refreshToken",
                refreshToken,
                jwtConfig.getRefreshTokenValidTime()
        );

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken); // Authorization 헤더에 토큰 추가
        responseHeaders.add(HttpHeaders.SET_COOKIE, cookie.toString());

        return responseHeaders;
    }


}

