package org.zerock.study.domain.user.DTO;

import lombok.Builder;

@Builder
public record SignupRequest(
        String email,
        String password
) {
}
