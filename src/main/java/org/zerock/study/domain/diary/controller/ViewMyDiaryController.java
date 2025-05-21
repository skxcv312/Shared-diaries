package org.zerock.study.domain.diary.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.study.domain.diary.Service.DiaryService;
import org.zerock.study.domain.diary.entity.DiaryEntity;
import org.zerock.study.global.jwtToken.JwtTokenProvider.MemberTokenInfo;
import org.zerock.study.global.jwtToken.MemberContext;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diaries/me")
public class ViewMyDiaryController {
    final DiaryService diaryService;


    // 내가 작성한 모든 일기 조회
    @GetMapping("")
    public ResponseEntity<?> getMyDiaries() {
        MemberTokenInfo memberTokenInfo = MemberContext.get();
        Long userId = memberTokenInfo.userId();
        List<DiaryEntity> diaryEntityList = diaryService.findAllMyDiary(userId);
        return ResponseEntity.ok(diaryEntityList);
    }

    // 내가 작성한 특정 일기 조회
    @GetMapping("/{diaryId}")
    public ResponseEntity<?> getMyDiaryById(@PathVariable Long diaryId) {
        MemberTokenInfo memberTokenInfo = MemberContext.get();
        Long userId = memberTokenInfo.userId();
        DiaryEntity diaryEntityList = diaryService.findMyDiary(userId, diaryId);
        return ResponseEntity.ok(diaryEntityList);
    }
}
