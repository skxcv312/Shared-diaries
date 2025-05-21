package org.zerock.study.global.jwtToken;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum Role {

    ROLE_USER("사용자"),
    ROLE_ADMIN("관리자"),
    ROLE_MANAGER("매니저");

    private final String description;
}
