package org.zerock.study.domain.user.DTO;

import lombok.Builder;

@Builder
public record SigninRequest(
        String email,
        String password
) {
}
