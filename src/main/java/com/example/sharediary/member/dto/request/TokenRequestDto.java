package com.example.sharediary.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class TokenRequestDto {
    private Long memberId;
    @NotBlank
    private String memberName;
    @NotBlank
    private String password;

    public TokenRequestDto() {
    }

    public TokenRequestDto(final Long memberId, String memberName, final String password) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.password = password;
    }

    public Long getMemberId(){return memberId;}

    public String getMemberName(){return memberName;}

    public String getPassword() {
        return password;
    }
}

