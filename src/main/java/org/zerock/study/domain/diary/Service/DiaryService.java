package org.zerock.study.domain.diary.Service;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.study.domain.auth.repository.MemberRepo;
import org.zerock.study.domain.diary.DTO.request.DiaryRequest.createDiaryRequest;
import org.zerock.study.domain.diary.DTO.request.DiaryRequest.updateDiaryRequest;
import org.zerock.study.domain.diary.entity.DiaryEntity;
import org.zerock.study.domain.auth.entity.MembersEntity;
import org.zerock.study.domain.diary.repository.DiaryRepo;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepo diaryRepo;
    private final MemberRepo memberRepo;

    // 일기 상세 조회
    public DiaryEntity findDiaryById(Long id) {
        return diaryRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Diary id is not exist"));
    }

    // 일기 삭제
    public void deleteDiary(Long id) {
        diaryRepo.deleteById(id);
    }

    // 유저 찾기
    public MembersEntity findMemberById(Long id) {
        return memberRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member Id is not exist"));
    }


    // 일기 추가
    public DiaryEntity createDiary(Long userId, createDiaryRequest newDiary) {
        MembersEntity membersEntity = findMemberById(userId);
        DiaryEntity diaryEntity = DiaryEntity.builder()
                .title(newDiary.title())
                .content(newDiary.content())
                .publicOn(newDiary.publicOn())
                .membersEntity(membersEntity)
                .build();
        return diaryRepo.save(diaryEntity);
    }

    // 일기 수정
    public DiaryEntity updateDiary(Long userId, Long diaryId, updateDiaryRequest editDiary) {
        // 다이어리 찾기
        DiaryEntity diaryEntity = findDiaryById(diaryId);

        // 일기 소유자 확인
        if (!Objects.equals(diaryEntity.getMembersEntity().getId(), userId)) {
            throw new RuntimeException("Different member IDs");
        }

        // 다이어리 수정
        diaryEntity.setTitle(editDiary.title());
        diaryEntity.setContent(editDiary.content());
        diaryEntity.setPublicOn(editDiary.publicOn());

        return diaryRepo.save(diaryEntity);
    }
}
