package com.sportify.userservice.infrastructure.jwt.aop;

import com.sportify.userservice.controllers.BaseController;
import com.sportify.userservice.infrastructure.jwt.JwtModel;
import com.sportify.userservice.infrastructure.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class JwtAuthenticatedAspect {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticatedAspect(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Before("@annotation(com.sportify.userservice.infrastructure.jwt.annotations.JwtAuthenticated) && args(request,..)")
    public void beforeJwtAuthenticatedMethod(JoinPoint joinPoint, HttpServletRequest request) {
        Object target = joinPoint.getTarget();
        if (target instanceof BaseController) {
            String token = request.getHeader("Authorization").substring(7);
            JwtModel jwtModel = jwtTokenProvider.getJwtModel(token);
            ((BaseController) target).setJwtModel(jwtModel);
        }
    }

    @After("@annotation(com.sportify.userservice.infrastructure.jwt.annotations.JwtAuthenticated)")
    public void afterJwtAuthenticatedMethod(JoinPoint joinPoint) {
        Object target = joinPoint.getTarget();
        if (target instanceof BaseController) {
            ((BaseController) target).clearJwtModel();
        }
    }
}

