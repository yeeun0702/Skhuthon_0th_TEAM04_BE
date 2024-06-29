package com.example.sharediary.friend.domain;

import com.example.sharediary.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long friendId;

    // Member와 관계 설정 - 요청 하는 사용자
    @ManyToOne
    @JoinColumn(nullable = false)
    private Member sender;

    // Member와 관계 설정 - 요청 받는 사용자
    @ManyToOne
    @JoinColumn(nullable = false)
    private Member receiver;

    // 교환일기 제목
    private String title;

    @Enumerated(EnumType.STRING)
    private FriendStatus status;
}
