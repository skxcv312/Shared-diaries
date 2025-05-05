package org.zerock.study.domain.DTO;

import lombok.Builder;

@Builder
public record SigninRequest(
        String email,
        String password
){}
