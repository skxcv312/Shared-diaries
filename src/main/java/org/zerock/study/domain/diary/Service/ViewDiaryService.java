package org.zerock.study.domain.diary.Service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.study.domain.diary.entity.DiaryEntity;
import org.zerock.study.domain.diary.repository.DiaryRepo;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ViewDiaryService {
    private final DiaryRepo diaryRepo;

    //유저 일기 조회
    public List<DiaryEntity> findAllMyDiary(Long userId) {
        return diaryRepo.findByMembersEntityId(userId);
    }

    // 유저 상세 일기 조회
    public DiaryEntity findMyDiary(Long userId, Long diaryId) {
        return diaryRepo.findByMembersEntityIdAndId(userId, diaryId);
    }

    // 공개 일기 조회
    public List<DiaryEntity> findAllPublicDiary() {
        return diaryRepo.findByPublicOnIsTrue();
    }

    // 공개 일기 상세 조회
    public DiaryEntity findPublicDiary(Long diaryId) {
        return diaryRepo.findByPublicOnIsTrueAndId(diaryId);
    }
}
