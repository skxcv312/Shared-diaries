package org.zerock.study.domain.auth.DTO;

public record SigningResponse(
        String accessToken,
        String refreshToken
) {
}
