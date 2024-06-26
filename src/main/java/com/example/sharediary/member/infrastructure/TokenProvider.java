package com.example.sharediary.member.infrastructure;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Date;

@Component
public class TokenProvider {
    @Value("${jwt.header}")
    private String COOKIE_NAME;
    @Value("${jwt.secret}")
    private String SECRET_KEY;
    @Value("${jwt.token-validity-in-seconds}")
    private long VALIDITY_IN_MILLISECONDS;
    public static final String LOGIN_REQUIRED = "로그인이 필요합니다.";
    public static final String NOT_AUTHORIZED_ADMIN_ROLE_REQUIRED = "권한이 없습니다. 관리자 권한이 필요합니다.";
    public static final String TOKEN_EXPIRED_LOGIN_REQUIRED = "로그인 기간이 만료되었습니다. 다시 로그인 해주세요.";

    public String createToken(final String payload) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + VALIDITY_IN_MILLISECONDS);

        return Jwts.builder()
                .setSubject(payload)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        final byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getPayload(final String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public String getTokenFromCookies(final HttpServletRequest request) {
        validateRequestCookies(request);
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(COOKIE_NAME))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new JwtException(LOGIN_REQUIRED));
    }

    private void validateRequestCookies(final HttpServletRequest request) {
        if (request.getCookies() == null) {
            throw new JwtException(LOGIN_REQUIRED);
        }
    }

    public void validateTokenExpiration(final String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw new JwtException(TOKEN_EXPIRED_LOGIN_REQUIRED);
        }
    }

    public void validateTokenRole(final String token) {
        final Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token);
        final boolean isNotAdmin = !claims.getBody().get("role").equals("ADMIN");

        if (isNotAdmin) {
            throw new JwtException(NOT_AUTHORIZED_ADMIN_ROLE_REQUIRED);
        }
    }
}
