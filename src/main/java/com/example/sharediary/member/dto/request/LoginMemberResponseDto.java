package com.example.sharediary.member.dto.request;

import com.example.sharediary.member.domain.Member;

public record LoginMemberResponseDto(Long id) {
    public static LoginMemberResponseDto from(final Member member) {
        return new LoginMemberResponseDto(member.getId());
    }
}
