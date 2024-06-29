package com.example.sharediary.comment.domain;


import com.example.sharediary.comment.dto.CommentRequestDto;
import com.example.sharediary.diary.domain.Diary;
import com.example.sharediary.friend.domain.Friend;
import com.example.sharediary.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID")
    private Long commentId;

    // 댓글 내용
    @Column(nullable = false)
    private String content;

    // 유저 한 명이 여러 개의 댓글을 달 수 있음.
    // Member클래스와 Comment 클래스 사이의 연관 관계 맵핑 가능/ 한 명의 사람이 여러 개의 댓글을 달 수 있음.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FRIEND_ID", nullable = false)
    private Friend friend;

    // 게시글 하나에 여러 개의 댓글들이 달릴 수 있음. 즉, '다'에 해당함으로 ManyToOne
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DIARY_ID")
    private Diary diary;

    public void update(CommentRequestDto commentRequestDto) {
        this.content = commentRequestDto.getContent();
    }

}
