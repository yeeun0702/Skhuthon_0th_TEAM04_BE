package com.example.sharediary.member.dto.response;

import com.example.sharediary.member.domain.Member;
import lombok.Getter;

@Getter
public class MemberResponseDto {
    private Long id;
    private String memberName;

    public MemberResponseDto(Member saveMember) {
        this(saveMember.getMemberId(), saveMember.getMemberName());
    }

    public MemberResponseDto(Long id, String memberName) {
        this.id = id;
        this.memberName = memberName;
    }

    public Long getId() {
        return id;
    }

    public String getMemberName() {
        return memberName;
    }

}
