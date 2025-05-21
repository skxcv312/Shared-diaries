package org.zerock.study.domain.diary.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.study.domain.diary.DTO.DiaryRequest.createDiaryRequest;
import org.zerock.study.domain.diary.DTO.DiaryRequest.updateDiaryRequest;
import org.zerock.study.domain.diary.entity.DiaryEntity;
import org.zerock.study.domain.auth.entity.MembersEntity;
import org.zerock.study.domain.diary.repository.DiaryRepo;

@SpringBootTest
class DiaryServiceTest {

    private static final Long MEMBER_ID = 1L;
    private static final Long DIARY_ID = 1L;

    @Mock
    private DiaryRepo diaryRepo;

    @InjectMocks
    private DiaryService diaryService;

    private MembersEntity membersEntity;
    private DiaryEntity diaryEntity;

    @BeforeEach
    void setUp() {
        membersEntity = MembersEntity.builder()
                .email("test@gmail.com")
                .id(MEMBER_ID)
                .password("123456")
                .build();

        diaryEntity = DiaryEntity.builder()
                .title("title1")
                .content("text1")
                .publicOn(true)
                .membersEntity(membersEntity)
                .build();
    }

    @Test
    void 일기_생성() {
        createDiaryRequest request = new createDiaryRequest("title1", "text1", true);

        when(diaryRepo.save(any(DiaryEntity.class))).thenReturn(diaryEntity);

        DiaryEntity savedDiary = diaryService.CreateDiary(membersEntity, request);

        assertNotNull(savedDiary);
        assertEquals("title1", savedDiary.getTitle());
        assertEquals("text1", savedDiary.getContent());
        assertTrue(savedDiary.getPublicOn());
    }

    @Test
    void 일기_수정() {
        updateDiaryRequest request = new updateDiaryRequest("editTitle", "editContent", false);

        when(diaryRepo.findById(DIARY_ID)).thenReturn(Optional.of(diaryEntity));
        when(diaryRepo.save(any(DiaryEntity.class))).thenReturn(diaryEntity);

        DiaryEntity updatedDiary = diaryService.UpdateDiary(membersEntity, DIARY_ID, request);

        assertNotNull(updatedDiary);
        assertEquals("editTitle", updatedDiary.getTitle());
        assertEquals("editContent", updatedDiary.getContent());
        assertFalse(updatedDiary.getPublicOn());
    }

    @Test
    void 일기_삭제() {
        doNothing().when(diaryRepo).deleteById(DIARY_ID);

        diaryService.DeleteDiary(DIARY_ID);

        verify(diaryRepo, times(1)).deleteById(DIARY_ID);
    }

    @Test
    void 유저_일기_조회() {
        when(diaryRepo.findByMembersEntityId(MEMBER_ID)).thenReturn(List.of(diaryEntity));

        List<DiaryEntity> diaries = diaryService.findAllMyDiary(membersEntity);

        assertNotNull(diaries);
        assertEquals(1, diaries.size());
        assertEquals("title1", diaries.get(0).getTitle());
    }

    @Test
    void 유저_상세_일기_조회() {
        when(diaryRepo.findByMembersEntityIdAndId(MEMBER_ID, DIARY_ID)).thenReturn(diaryEntity);

        DiaryEntity diary = diaryService.findMyDiary(membersEntity, DIARY_ID);

        assertNotNull(diary);
        assertEquals("title1", diary.getTitle());
    }

    @Test
    void 공개_일기_조회() {
        when(diaryRepo.findByPublicOnIsTrue()).thenReturn(List.of(diaryEntity));

        List<DiaryEntity> diaries = diaryService.findAllPublicDiary();

        assertNotNull(diaries);
        assertEquals(1, diaries.size());
        assertTrue(diaries.get(0).getPublicOn());
    }

    @Test
    void 공개_일기_상세_조회() {
        when(diaryRepo.findByPublicOnIsTrueAndId(DIARY_ID)).thenReturn(diaryEntity);

        DiaryEntity diary = diaryService.findPublicDiary(DIARY_ID);

        assertNotNull(diary);
        assertTrue(diary.getPublicOn());
    }
}
