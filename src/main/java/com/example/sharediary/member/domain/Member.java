package com.example.sharediary.member.domain;

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
    private Long id;

    private String memberName;
    private String password;
    private String name;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Diary> diaries;

    @Builder
    public Member(String memberName, String password, String name) {
        this.memberName = memberName;
        this.password = password;
        this.name = name;
    }
}
