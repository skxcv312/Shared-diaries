package org.zerock.study.domain.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.study.domain.entity.DiaryEntity;

@Repository
public interface DiaryRepo extends JpaRepository<DiaryEntity, Long> {
    List<DiaryEntity> findByPublicOnIsTrue(); // 공개된 일기만 조회

    List<DiaryEntity> findByMembersEntityId(Long membersEntityId); // 내 일기만 조회

    DiaryEntity findByMembersEntityIdAndId(Long membersEntityId, Long id);

    DiaryEntity findByPublicOnIsTrueAndId(Long id);
}
