package org.zerock.study.domain.auth.DTO.response;

public record SigningResponse(
        String accessToken,
        String refreshToken
) {
}
