package com.example.sharediary.member.controller;

import com.example.sharediary.member.dto.request.MemberRequestDto;
import com.example.sharediary.member.dto.request.TokenRequestDto;
import com.example.sharediary.member.dto.response.MemberResponseDto;
import com.example.sharediary.member.dto.response.TokenResponseDto;
import com.example.sharediary.member.service.CookieService;
import com.example.sharediary.member.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Tag(name = "사용자 API", description = "Swagger 사용자 API")
@RestController
@RequestMapping("/apis/v1/members")
public class MemberController {
    private final MemberService memberService;
    private final CookieService cookieService = new CookieService();

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 회원가입
    @PostMapping
    public ResponseEntity<MemberResponseDto> save(@RequestBody final MemberRequestDto request) {
        MemberResponseDto response = memberService.save(request);
        return ResponseEntity.created(URI.create("/members")).body(response);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody final TokenRequestDto request, final HttpServletResponse response) {
        final TokenResponseDto tokenResponse = memberService.createToken(request);
        final Cookie cookie = cookieService.createCookie(tokenResponse);
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }
}
