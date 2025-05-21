package org.zerock.study.domain.diary.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.study.domain.diary.DTO.DiaryRequest.createDiaryRequest;
import org.zerock.study.domain.diary.DTO.DiaryRequest.updateDiaryRequest;
import org.zerock.study.domain.diary.Service.DiaryService;
import org.zerock.study.domain.diary.entity.DiaryEntity;
import org.zerock.study.domain.auth.entity.MembersEntity;
import org.zerock.study.global.jwtToken.MemberContext;

@Log4j2
@RestController
@RequestMapping("/diaries")
@RequiredArgsConstructor
public class DiaryController {
    final DiaryService diaryService;

    // 새로운 일기 생성
    @PostMapping("")
    public ResponseEntity<?> createDiary(@RequestBody createDiaryRequest createDiaryRequest) {
        MembersEntity membersEntity = MemberContext.get();
        DiaryEntity diaryEntity = diaryService.CreateDiary(membersEntity, createDiaryRequest);
        return ResponseEntity.ok(diaryEntity);
    }

    // 기존 일기 수정
    @PatchMapping("/{diaryId}")
    public ResponseEntity<?> editDiary(@PathVariable Long diaryId, @RequestBody updateDiaryRequest updateDiaryRequest) {
        MembersEntity membersEntity = MemberContext.get();
        DiaryEntity diaryEntity = diaryService.UpdateDiary(membersEntity, diaryId, updateDiaryRequest);
        return ResponseEntity.ok(diaryEntity);
    }

    // 특정 일기 삭제
    @DeleteMapping("/{diaryId}")
    public ResponseEntity<?> removeDiary(@PathVariable Long diaryId) {
        diaryService.DeleteDiary(diaryId);
        return ResponseEntity.ok().build();
    }

    // 내가 작성한 모든 일기 조회
    @GetMapping("/me")
    public ResponseEntity<?> getMyDiaries() {
        MembersEntity membersEntity = MemberContext.get();
        List<DiaryEntity> diaryEntityList = diaryService.findAllMyDiary(membersEntity);
        return ResponseEntity.ok(diaryEntityList);
    }

    // 내가 작성한 특정 일기 조회
    @GetMapping("/me/{diaryId}")
    public ResponseEntity<?> getMyDiaryById(@PathVariable Long diaryId) {
        MembersEntity membersEntity = MemberContext.get();
        DiaryEntity diaryEntityList = diaryService.findMyDiary(membersEntity, diaryId);
        return ResponseEntity.ok(diaryEntityList);
    }

    // 공개된 모든 일기 조회
    @GetMapping("/public")
    public ResponseEntity<?> getPublicDiaries() {
        List<DiaryEntity> diaryEntityList = diaryService.findAllPublicDiary();
        return ResponseEntity.ok(diaryEntityList);
    }

    // 공개된 특정 일기 조회
    @GetMapping("/public/{diaryId}")
    public ResponseEntity<?> getPublicDiaryById(@PathVariable Long diaryId) {
        DiaryEntity diaryEntity = diaryService.findPublicDiary(diaryId);
        return ResponseEntity.ok(diaryEntity);
    }
}
