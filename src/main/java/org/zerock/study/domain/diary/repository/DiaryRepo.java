package org.zerock.study.domain.diary.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.study.domain.diary.entity.DiaryEntity;

@Repository
public interface DiaryRepo extends JpaRepository<DiaryEntity, Long> {
    List<DiaryEntity> findByPublicOnIsTrue(); // 공개된 일기만 조회

    List<DiaryEntity> findByMembersEntityId(Long membersEntityId); // 내 일기만 조회

    DiaryEntity findByMembersEntityIdAndId(Long membersEntityId, Long id);

    DiaryEntity findByPublicOnIsTrueAndId(Long id);
}
