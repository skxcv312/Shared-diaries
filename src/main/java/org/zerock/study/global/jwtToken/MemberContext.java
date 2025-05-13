package org.zerock.study.global.jwtToken;

import org.zerock.study.domain.entity.MembersEntity;

public class MemberContext {
    private static final ThreadLocal<MembersEntity> loginMember = new ThreadLocal<>();

    public static void set(MembersEntity member) {
        loginMember.set(member);
    }

    public static MembersEntity get() {
        return loginMember.get();
    }

    public static void clear() {
        loginMember.remove();
    }
}
