package org.zerock.study.domain.profile.DTO.response;


import lombok.Builder;

@Builder
public record ProfileResponse(
        String email,
        String createdAt
) {
}
