package org.zerock.study.domain.diary.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.study.domain.diary.DTO.request.DiaryRequest.createDiaryRequest;
import org.zerock.study.domain.diary.DTO.request.DiaryRequest.updateDiaryRequest;
import org.zerock.study.domain.diary.Service.DiaryService;
import org.zerock.study.domain.diary.entity.DiaryEntity;
import org.zerock.study.global.jwtToken.JwtTokenProvider.MemberTokenInfo;
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
        MemberTokenInfo memberTokenInfo = MemberContext.get();
        Long userId = memberTokenInfo.userId();
        DiaryEntity diaryEntity = diaryService.createDiary(userId, createDiaryRequest);
        return ResponseEntity.ok(diaryEntity);
    }

    // 기존 일기 수정
    @PatchMapping("/{diaryId}")
    public ResponseEntity<?> editDiary(@PathVariable Long diaryId, @RequestBody updateDiaryRequest updateDiaryRequest) {
        MemberTokenInfo memberTokenInfo = MemberContext.get();
        Long userId = memberTokenInfo.userId();
        DiaryEntity diaryEntity = diaryService.updateDiary(userId, diaryId, updateDiaryRequest);
        return ResponseEntity.ok(diaryEntity);
    }

    // 특정 일기 삭제
    @DeleteMapping("/{diaryId}")
    public ResponseEntity<?> removeDiary(@PathVariable Long diaryId) {
        diaryService.deleteDiary(diaryId);
        return ResponseEntity.ok().build();
    }
}
