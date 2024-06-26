package com.example.sharediary.member.dto.response;

import com.example.sharediary.member.domain.Member;
import lombok.Getter;

@Getter
public class MemberResponseDto {
    private Long id;
    private String name;

    public MemberResponseDto(Member saveMember) {
        this(saveMember.getId(), saveMember.getName());
    }

    public MemberResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
