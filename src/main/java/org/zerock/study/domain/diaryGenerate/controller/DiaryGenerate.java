package org.zerock.study.domain.diaryGenerate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.study.domain.diaryGenerate.DTO.request.GenerateDiaryRequest;
import org.zerock.study.domain.diaryGenerate.DTO.response.GenerateDiaryResponse;
import org.zerock.study.domain.diaryGenerate.service.DiaryGenerateService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/diary/generate/")
public class DiaryGenerate {
    private final DiaryGenerateService diaryGenerateService;

    @PostMapping()
    ResponseEntity<?> generateDiary(@RequestBody GenerateDiaryRequest request) {
        String diary = request.content();

        String CompletedSeries = diaryGenerateService.completeDiaryIntegration(diary);

        return ResponseEntity.ok(new GenerateDiaryResponse(CompletedSeries));
    }


}
