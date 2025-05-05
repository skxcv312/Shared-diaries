package org.zerock.study.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.study.domain.entity.Members;

@Repository
public interface MemberRepo extends JpaRepository<Members, Long> {
    public Members findMembersByEmail(String email);

    public boolean existsByEmail(String email);


}
