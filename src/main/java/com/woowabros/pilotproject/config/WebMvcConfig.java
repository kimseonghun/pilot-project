package com.woowabros.pilotproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final HandlerMethodArgumentResolver sessionMemberArgumentResolver;

    public WebMvcConfig(HandlerMethodArgumentResolver sessionMemberArgumentResolver) {
        this.sessionMemberArgumentResolver = sessionMemberArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(sessionMemberArgumentResolver);
    }
}
