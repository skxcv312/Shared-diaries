package org.zerock.study.domain.Diary.DTO;


import lombok.Builder;
import org.zerock.study.domain.entity.MembersEntity;

public class DiaryRequest {

    @Builder
    public record createDiaryRequest(String title, String content, Boolean publicOn) {
    }

    @Builder
    public record updateDiaryRequest(String title, String content, Boolean publicOn) {
    }

}

