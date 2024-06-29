package com.example.sharediary.diary.domain;

import com.example.sharediary.diary.dto.DiaryRequestDto;
import com.example.sharediary.friend.domain.Friend; 

import com.example.sharediary.member.domain.Member;
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
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "DIARY_ID")
    private Long diaryId;

    // Member와의 관계 설정
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

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


    public void update(DiaryRequestDto diaryRequestDto) {
        this.title = diaryRequestDto.getTitle();
        this.content = diaryRequestDto.getContent();
        this. sing = diaryRequestDto.getSing();
    }
}
