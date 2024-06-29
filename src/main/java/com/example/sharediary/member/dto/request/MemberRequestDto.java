package com.example.sharediary.member.dto.request;

public class MemberRequestDto {

    private String password;
    private String senderName;

    public MemberRequestDto(){}

    public MemberRequestDto( final String password, final String memberName) {
        this.password = password;
        this.senderName = memberName;
    }


    public String getPassword() {
        return password;
    }

    public String getSenderName() {
        return senderName;
    }

}
