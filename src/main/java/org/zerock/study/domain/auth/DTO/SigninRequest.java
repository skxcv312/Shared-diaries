package org.zerock.study.domain.auth.DTO;

import lombok.Builder;

@Builder
public record SigninRequest(
        String email,
        String password
) {
}
