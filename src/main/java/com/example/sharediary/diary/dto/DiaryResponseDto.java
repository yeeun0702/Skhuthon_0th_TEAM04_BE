package com.example.sharediary.diary.dto;

import com.example.sharediary.diary.domain.Diary;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class DiaryResponseDto {
    private Long diaryId;
    private String title;
    private String content;
    private String sing;
    private Long heartCount;
    private String friendName;

    // Diary 객체를 DiaryResponseDto 객체로 변환
    public static DiaryResponseDto of(Diary diary) {
        return DiaryResponseDto.builder()
                .diaryId(diary.getDiaryId())
                .title(diary.getTitle())
                .content(diary.getContent())
                .heartCount(diary.getHeartCount())
                .sing(diary.getSing())
                .build();
    }
}
