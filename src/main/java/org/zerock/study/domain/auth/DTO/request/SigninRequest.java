package org.zerock.study.domain.auth.DTO.request;

import lombok.Builder;

@Builder
public record SigninRequest(
        String email,
        String password
) {
}
