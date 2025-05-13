package org.zerock.study.domain.Diary.Service;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.study.domain.Diary.DTO.DiaryRequest.createDiaryRequest;
import org.zerock.study.domain.Diary.DTO.DiaryRequest.updateDiaryRequest;
import org.zerock.study.domain.entity.DiaryEntity;
import org.zerock.study.domain.entity.MembersEntity;
import org.zerock.study.domain.repository.DiaryRepo;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepo diaryRepo;

    // 일기 상세 조회
    public DiaryEntity findDiaryById(Long id) {
        return diaryRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Diary id is not exist"));
    }

    // 일기 삭제
    public void DeleteDiary(Long id) {
        diaryRepo.deleteById(id);
    }


    // 일기 추가
    public DiaryEntity CreateDiary(MembersEntity membersEntity, createDiaryRequest newDiary) {
        DiaryEntity diaryEntity = DiaryEntity.builder()
                .title(newDiary.title())
                .content(newDiary.content())
                .publicOn(newDiary.publicOn())
                .membersEntity(membersEntity)
                .build();
        return diaryRepo.save(diaryEntity);
    }

    // 일기 수정
    public DiaryEntity UpdateDiary(MembersEntity membersEntity, Long DiaryId, updateDiaryRequest editDiary) {
        // 다이어리 찾기
        DiaryEntity diaryEntity = findDiaryById(DiaryId);

        // 일기 소유자 확인
        if (!Objects.equals(diaryEntity.getMembersEntity().getId(), membersEntity.getId())) {
            throw new RuntimeException("Different member IDs");
        }

        // 다이어리 수정
        diaryEntity.setTitle(editDiary.title());
        diaryEntity.setContent(editDiary.content());
        diaryEntity.setPublicOn(editDiary.publicOn());

        return diaryRepo.save(diaryEntity);
    }

    //유저 일기 조회
    public List<DiaryEntity> findAllMyDiary(MembersEntity member) {
        return diaryRepo.findByMembersEntityId(member.getId());
    }

    // 유저 상세 일기 조회
    public DiaryEntity findMyDiary(MembersEntity member, Long DiaryId) {
        return diaryRepo.findByMembersEntityIdAndId(member.getId(), DiaryId);
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
