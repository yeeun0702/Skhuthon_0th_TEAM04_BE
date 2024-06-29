package com.example.sharediary.member.service;

import com.example.sharediary.member.domain.Member;
import com.example.sharediary.member.dto.request.LoginMemberResponseDto;
import com.example.sharediary.member.dto.request.MemberRequestDto;
import com.example.sharediary.member.dto.request.TokenRequestDto;
import com.example.sharediary.member.dto.response.MemberResponseDto;
import com.example.sharediary.member.dto.response.TokenResponseDto;
import com.example.sharediary.member.infrastructure.TokenProvider;
import com.example.sharediary.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    public MemberService(MemberRepository memberRepository, TokenProvider tokenProvider) {
        this.memberRepository = memberRepository;
        this.tokenProvider = tokenProvider;
    }


    // 회원가입
    public MemberResponseDto save(final MemberRequestDto request) {
        final String senderName = request.getSenderName();
        final String password = request.getPassword();

        if (memberRepository.findBySenderName(senderName).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }

            final Member member = new Member(senderName, password);

            return new MemberResponseDto(memberRepository.save(member));
    }


    public MemberResponseDto createToken(final TokenRequestDto request) {
        final Member member = memberRepository.findBySenderNameAndPassword(request.getSenderName(), request.getPassword());

        final TokenResponseDto tokenResponse = new TokenResponseDto(tokenProvider.createToken(member.getSenderName()));

        if (member == null) {
            throw new IllegalArgumentException("잘못된 아이디 또는 비밀번호입니다.");
        }

        return new MemberResponseDto(member.getMemberId(), member.getSenderName(), tokenResponse);
    }

    @Transactional(readOnly = true)
    public LoginMemberResponseDto findLoginMemberByToken(final String token) {
        final String senderName = tokenProvider.getPayload(token);
        final Member member = memberRepository.findBySenderName(senderName)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        return LoginMemberResponseDto.from(member);
    }

//    @Transactional(readOnly = true)
//    public LoginMemberResponseDto findLoginMemberByToken(final String token) {
//        final String memberName = tokenProvider.getPayload(token);
//        final Member member = memberRepository.findByMemberName(memberName);
//        if (member == null) {
//            throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
//        }
//        return LoginMemberResponseDto.from(member);
//    }
}
