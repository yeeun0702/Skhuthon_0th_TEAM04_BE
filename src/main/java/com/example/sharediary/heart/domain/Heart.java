package com.example.sharediary.heart.domain;

import com.example.sharediary.diary.domain.Diary;
import com.example.sharediary.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Heart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "heartId")
    private Long heartId;

    // Member와 관계 설정
    @ManyToOne
    @JoinColumn(name = "senderId", nullable = false)
    private Member member;

    // Diary와 관계 설정
    @ManyToOne
    @JoinColumn(name = "diaryId", nullable = false)
    private Diary diary;

    @Builder
    public Heart(Member member, Diary diary) {
        this.member = member;
        this.diary = diary;
    }
}
