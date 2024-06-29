package com.example.sharediary.member.dto.response;

import com.example.sharediary.member.domain.Member;
import lombok.Getter;

@Getter
public class MemberResponseDto {
    private Long senderId;
    private String senderName;

    public MemberResponseDto(Member saveMember) {
        this(saveMember.getMemberId(), saveMember.getSenderName());
    }

    public MemberResponseDto(Long senderId, String senderName) {
        this.senderId = senderId;
        this.senderName = senderName;
    }

    public Long getSenderId() {
        return senderId;
    }

    public String getSenderName() {
        return senderName;
    }

}
