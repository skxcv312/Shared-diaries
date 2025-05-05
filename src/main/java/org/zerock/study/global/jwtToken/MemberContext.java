package org.zerock.study.global.jwtToken;

import org.zerock.study.domain.entity.Members;

public class MemberContext {
    private static final ThreadLocal<Members> loginMember = new ThreadLocal<>();

    public static void set(Members member) {
        loginMember.set(member);
    }

    public static Members get() {
        return loginMember.get();
    }

    public static void clear() {
        loginMember.remove();
    }
}
