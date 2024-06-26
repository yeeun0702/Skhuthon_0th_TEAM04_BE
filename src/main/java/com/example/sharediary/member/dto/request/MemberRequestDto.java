package com.example.sharediary.member.dto.request;

public class MemberRequestDto {
    private String memberName;
    private String password;
    private String name;

    public MemberRequestDto(){}

    public MemberRequestDto(final String memberName, final String password, final String name) {
        this.memberName = memberName;
        this.password = password;
        this.name = name;
    }

    public String getmemberName() {
        return memberName;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

}
