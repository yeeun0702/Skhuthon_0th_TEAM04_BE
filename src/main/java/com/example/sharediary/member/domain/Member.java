package com.example.sharediary.member.domain;

import com.example.sharediary.comment.domain.Comment;
import com.example.sharediary.diary.domain.Diary;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long memberId;

    private String password;
    private String memberName;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Diary> diaries;

    @OneToMany(mappedBy = "member")
    private List<Comment>  comments;

    @Builder
    public Member(String memberName, String password) {
        this.password = password;
        this.memberName = memberName;
    }
}
