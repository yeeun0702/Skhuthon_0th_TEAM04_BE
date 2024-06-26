package com.example.sharediary.member.config;

import com.example.sharediary.member.infrastructure.AuthorizationHandlerInterceptor;
import com.example.sharediary.member.infrastructure.LoginMemberArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    private final LoginMemberArgumentResolver loginMemberArgumentResolver;
    private final AuthorizationHandlerInterceptor authorizationHandlerInterceptor;

    public WebMvcConfiguration(final LoginMemberArgumentResolver loginMemberArgumentResolver,
                               final AuthorizationHandlerInterceptor authorizationHandlerInterceptor) {
        this.loginMemberArgumentResolver = loginMemberArgumentResolver;
        this.authorizationHandlerInterceptor = authorizationHandlerInterceptor;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginMemberArgumentResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationHandlerInterceptor)
                .addPathPatterns("/admin/**");
    }
}