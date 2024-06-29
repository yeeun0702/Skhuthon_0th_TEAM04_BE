package com.example.sharediary.member.service;

import com.example.sharediary.member.dto.response.TokenResponseDto;
import jakarta.servlet.http.Cookie;

import java.util.Arrays;

public class CookieService {
    protected static final String COOKIE_NAME = "token";

    public Cookie createCookie(final TokenResponseDto tokenResponse) {
        final Cookie cookie = new Cookie(COOKIE_NAME, tokenResponse.accessToken());
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setAttribute("SameSite", "None");
        cookie.setSecure(true);

        return cookie;
    }

    public Cookie createEmptyCookie() {
        final Cookie cookie = new Cookie(COOKIE_NAME, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        return cookie;
    }

    public String extractCookies(final Cookie[] cookies) {
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(COOKIE_NAME))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new IllegalArgumentException("토큰이 존재하지 않습니다."));
    }
}