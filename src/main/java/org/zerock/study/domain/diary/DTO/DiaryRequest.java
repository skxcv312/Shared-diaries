package org.zerock.study.domain.diary.DTO;


import lombok.Builder;

public class DiaryRequest {

    @Builder
    public record createDiaryRequest(String title, String content, Boolean publicOn) {
    }

    @Builder
    public record updateDiaryRequest(String title, String content, Boolean publicOn) {
    }

}

