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
    private Long friendId;
    private String friendName;


    public static CommentResponseDto of(Comment comment){
        return CommentResponseDto.builder()
                .commentId(comment.getCommentId())
                .content(comment.getContent())
              //  .friendName(comment.getFriend().getFriendId())
                .build();
    }
}
