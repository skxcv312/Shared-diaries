package org.zerock.study.domain.diary.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.study.domain.diary.Service.ViewDiaryService;
import org.zerock.study.domain.diary.entity.DiaryEntity;


@RestController
@RequestMapping("/diaries/public")
@RequiredArgsConstructor
public class ViewPublicDiaryController {
    private final ViewDiaryService viewDiaryService;

    // 공개된 모든 일기 조회
    @GetMapping("")
    public ResponseEntity<?> getPublicDiaries() {
        List<DiaryEntity> diaryEntityList = viewDiaryService.findAllPublicDiary();
        return ResponseEntity.ok(diaryEntityList);
    }

    // 공개된 특정 일기 조회
    @GetMapping("/{diaryId}")
    public ResponseEntity<?> getPublicDiaryById(@PathVariable Long diaryId) {
        DiaryEntity diaryEntity = viewDiaryService.findPublicDiary(diaryId);
        return ResponseEntity.ok(diaryEntity);
    }
}
