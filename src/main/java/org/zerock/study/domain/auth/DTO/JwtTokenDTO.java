package org.zerock.study.domain.auth.DTO;

import lombok.Builder;

@Builder
public record JwtTokenDTO(
        String accessToken,
        String refreshToken
) {
}
