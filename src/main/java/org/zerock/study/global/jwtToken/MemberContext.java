package org.zerock.study.global.jwtToken;

import org.zerock.study.domain.auth.entity.MembersEntity;
import org.zerock.study.global.jwtToken.JwtTokenProvider.MemberTokenInfo;

public class MemberContext {
    private static final ThreadLocal<MemberTokenInfo> loginMember = new ThreadLocal<>();

    public static void set(MemberTokenInfo member) {
        loginMember.set(member);
    }

    public static MemberTokenInfo get() {
        return loginMember.get();
    }

    public static void clear() {
        loginMember.remove();
    }
}
