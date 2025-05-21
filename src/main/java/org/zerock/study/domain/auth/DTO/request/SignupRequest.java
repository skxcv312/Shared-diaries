package org.zerock.study.domain.auth.DTO.request;

import lombok.Builder;

@Builder
public record SignupRequest(
        String email,
        String password
) {
}
