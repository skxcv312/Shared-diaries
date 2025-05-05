package org.zerock.study.domain.DTO;

import lombok.Builder;

@Builder
public record SignupRequest(
        String name,
        String email,
        String password
) {
}
