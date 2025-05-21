package org.zerock.study.domain.auth.DTO;

import lombok.Builder;

@Builder
public record SignupRequest(
        String email,
        String password
) {
}
