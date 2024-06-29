package com.example.sharediary.diary.dto;

import com.example.sharediary.friend.domain.Friend;
import com.example.sharediary.heart.domain.Heart;
import com.example.sharediary.member.domain.Member;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성자를 통해서 값 변경 목적으로 접근하는 메시지 차단
public class DiaryRequestDto {

    private Long diaryId;
    private Long friendId;
    private String content;
    private String title;
    private String sing;
    private Heart heart;
    private Long heartCount;
    private String senderName;

}