package com.example.sharediary.comment.dto;

import com.example.sharediary.comment.domain.Comment;
import com.example.sharediary.member.domain.Member;
import lombok.*;


@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class CommentResponseDto {
    private Long commentId;
    private String content;
    private String senderName;


    public static CommentResponseDto of(Comment comment){
        return CommentResponseDto.builder()
                .commentId(comment.getCommentId())
                .content(comment.getContent())
                .senderName(comment.getSender().getSenderName())
                .build();
    }
}
