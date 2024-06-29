package com.example.sharediary.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class TokenRequestDto {

    @NotBlank
    private String memberName;
    @NotBlank
    private String password;

    public TokenRequestDto() {
    }

    public TokenRequestDto( String memberName, final String password) {
        this.memberName = memberName;
        this.password = password;
    }


    public String getMemberName(){return memberName;}

    public String getPassword() {
        return password;
    }
}

