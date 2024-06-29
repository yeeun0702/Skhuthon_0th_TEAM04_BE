package com.example.sharediary.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class TokenRequestDto {

    @NotBlank
    private String senderName;
    @NotBlank
    private String password;

    public TokenRequestDto() {
    }

    public TokenRequestDto( String senderName, final String password) {
        this.senderName = senderName;
        this.password = password;
    }


    public String getSenderName(){return senderName;}

    public String getPassword() {
        return password;
    }
}

