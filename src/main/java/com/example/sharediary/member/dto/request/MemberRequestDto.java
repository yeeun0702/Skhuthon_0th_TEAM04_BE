package com.example.sharediary.member.dto.request;

public class MemberRequestDto {
    private Long memberId;
    private String password;
    private String memberName;

    public MemberRequestDto(){}

    public MemberRequestDto(final Long memberId, final String password, final String memberName) {
        this.memberId = memberId;
        this.password = password;
        this.memberName = memberName;
    }

    public Long getMemberId(){return memberId;}

    public String getPassword() {
        return password;
    }

    public String getMemberName() {
        return memberName;
    }

}
