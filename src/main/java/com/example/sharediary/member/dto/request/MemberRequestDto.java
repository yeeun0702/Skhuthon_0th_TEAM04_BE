package com.example.sharediary.member.dto.request;

public class MemberRequestDto {

    private String password;
    private String memberName;

    public MemberRequestDto(){}

    public MemberRequestDto( final String password, final String memberName) {
        this.password = password;
        this.memberName = memberName;
    }


    public String getPassword() {
        return password;
    }

    public String getMemberName() {
        return memberName;
    }

}
