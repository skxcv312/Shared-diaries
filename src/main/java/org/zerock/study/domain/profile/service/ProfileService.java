package org.zerock.study.domain.profile.service;


import java.lang.reflect.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.study.domain.auth.entity.MembersEntity;
import org.zerock.study.domain.auth.repository.MemberRepo;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final MemberRepo memberRepo;

    public String getMemberCreatAt(Long UserId) {
        MembersEntity member = memberRepo.findById(UserId)
                .orElseThrow(() -> new IllegalArgumentException("User id is not None"));
        return member.getCreatedAt().toString();
    }
}
