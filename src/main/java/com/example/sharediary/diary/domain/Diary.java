package com.example.sharediary.diary.domain;

import com.example.sharediary.comment.domain.Comment;
import com.example.sharediary.diary.dto.DiaryRequestDto;
import com.example.sharediary.friend.domain.Friend;

import com.example.sharediary.member.domain.Member;
import com.example.sharediary.time.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Diary extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "DIARY_ID")
    private Long diaryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SENDER_ID", nullable = false)
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECEIVER_ID", nullable = false)
    private Member receiver;

    @Column(name = "HEART_COUNT")
    private Long heartCount =0L;

    // 제목
    @Column(nullable = false)
    private String title;

    // 내용
    @Column(name = "DIARY_CONTENT", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "SING", nullable = false)
    private String sing;


    // 댓글
    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public void update(DiaryRequestDto diaryRequestDto) {
        this.title = diaryRequestDto.getTitle();
        this.content = diaryRequestDto.getContent();
        this. sing = diaryRequestDto.getSing();
    }
}
