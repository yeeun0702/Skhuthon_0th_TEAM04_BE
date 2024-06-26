package com.example.sharediary.member.infrastructure;

import com.example.sharediary.member.dto.request.LoginMemberResponseDto;
import com.example.sharediary.member.service.MemberService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    private final MemberService memberService;
    private final TokenProvider tokenProvider;

    public LoginMemberArgumentResolver(final MemberService memberService, final TokenProvider tokenProvider) {
        this.memberService = memberService;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) { // MethodParameter를 매개변수로 받아옴 ,,
        // 함수의 인자로 넘어온 타입을 가져옴 -> 가져온 타입이 LogMemberResponseDto와 같으면 지원해주겠다.
        return parameter.getParameterType().equals(LoginMemberResponseDto.class);
    }

    // 지원해주는 방식 -> ArgumentResolver를 통해서 쿠키에 있는 값을 넘겨옴...
    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
        final HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        final String token = tokenProvider.getTokenFromCookies(request);

        try {
            return memberService.findLoginMemberByToken(token);
        } catch (JwtException e) {
            throw new JwtException("로그인이 필요합니다.");
        }
    }
}

