package org.zerock.study.global.jwtToken;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;


import javax.crypto.SecretKey;
import java.util.Date;
import org.zerock.study.config.JwtConfig;
import org.zerock.study.domain.entity.Members;
import org.zerock.study.domain.repository.MemberRepo;
import org.zerock.study.global.util.JsonUtils;


@Slf4j
@Configuration
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final MemberRepo memberRepo;
    private final JwtConfig jwtConfig;
    private final JsonUtils jsonUtils;

    private String setAccessToken(Members user) {
        Date now = new Date();
        String userJson = jsonUtils.toJson(user);

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("userInfo", userJson) // 정보 저장
                .issuedAt(now) // 토큰 발행 시간 정보
                .expiration(new Date(now.getTime() + jwtConfig.getAccessTokenValidTime())) // 토큰 유효시각 설정
                .signWith(jwtConfig.getSecretKey())  // 암호화 알고리즘과, secret 값
                .compact();
    }


    private String setRefreshToken(Members user) {
        Date now = new Date();
        return Jwts.builder()
                .subject(user.getEmail())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + jwtConfig.getRefreshTokenValidTime()))
                .signWith(jwtConfig.getSecretKey())
                .compact();
    }

    // 토큰 생성
    public JwtTokenDTO createToken(Members user) {  // userPK = email

        String accessToken = setAccessToken(user);
        String refreshToken = setRefreshToken(user);
        return new JwtTokenDTO(accessToken, refreshToken);
    }

    // 리프레시토큰으로 엑세스토큰얻기
    public JwtTokenDTO getTokenWithRefresh(String refreshToken) {
        String userEmail = getUserInfo(refreshToken);
        Members user = memberRepo.findMembersByEmail(userEmail);
        return createToken(user);
    }

    // 인증 정보 조회
    public Members getAuthentication(String token) {
        String userEmail = getUserInfo(token);
        return memberRepo.findMembersByEmail(userEmail);
    }

    // 토큰에서 회원 이메일 정보 추출
    public String getUserInfo(String token) {
        Claims claims = validateToken(token);
        return claims.getSubject();
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

