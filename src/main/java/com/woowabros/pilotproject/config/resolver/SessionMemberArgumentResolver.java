package com.woowabros.pilotproject.config.resolver;

import com.woowabros.pilotproject.config.resolver.exception.NotExistSessionMemberException;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Component
public class SessionMemberArgumentResolver implements HandlerMethodArgumentResolver {
    public static final String SESSION_MEMBER = "sessionMember";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return SessionMember.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession();
        SessionMember sessionMember = (SessionMember) session.getAttribute(SESSION_MEMBER);

        if (Objects.isNull(sessionMember)) {
            throw new NotExistSessionMemberException();
        }

        return sessionMember;
    }
}
