package com.example.sharediary.member.config;

import com.example.sharediary.member.infrastructure.AuthorizationHandlerInterceptor;
import com.example.sharediary.member.infrastructure.LoginMemberArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
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
                .addPathPatterns("/admin/**")
                .excludePathPatterns("http://localhost:5173")
                .excludePathPatterns("http://localhost:8080");
    }
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("Content-Type")
                .exposedHeaders("Custom-Header")
                .allowCredentials(true)
                .maxAge(3600);

    }

}