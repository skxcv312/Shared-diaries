package org.zerock.study.global.jwtToken;

import lombok.Builder;

@Builder
public record JwtTokenDTO(
        String accessToken,
        String refreshToken
) {
}
