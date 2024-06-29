package com.example.sharediary.comment.dto;

import com.example.sharediary.diary.domain.Diary;
import com.example.sharediary.member.domain.Member;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Getter
public class CommentRequestDto {
    private Long commentId;
    private String content;
    private Long memberId;
    private Diary diary;
}
