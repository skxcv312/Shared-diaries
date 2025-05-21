package org.zerock.study.domain.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.study.domain.auth.entity.MembersEntity;

@Repository
public interface MemberRepo extends JpaRepository<MembersEntity, Long> {
    public MembersEntity findMembersByEmail(String email);

    public boolean existsByEmail(String email);


}
