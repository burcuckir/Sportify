package org.sportify.jwt.aop;

import jakarta.servlet.http.HttpServletRequest;
import org.sportify.controller.BaseController;
import org.sportify.jwt.JwtModel;
import org.sportify.jwt.JwtTokenProvider;
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

    @Before("@annotation(org.sportify.jwt.annotations.JwtAuthenticated) && args(request,..)")
    public void beforeJwtAuthenticatedMethod(JoinPoint joinPoint, HttpServletRequest request) {
        Object target = joinPoint.getTarget();
        if (target instanceof BaseController) {
            String token = request.getHeader("Authorization").substring(7);
            JwtModel jwtModel = jwtTokenProvider.getJwtModel(token);
            ((BaseController) target).setJwtModel(jwtModel);
        }
    }

    @After("@annotation(org.sportify.jwt.annotations.JwtAuthenticated)")
    public void afterJwtAuthenticatedMethod(JoinPoint joinPoint) {
        Object target = joinPoint.getTarget();
        if (target instanceof BaseController) {
            ((BaseController) target).clearJwtModel();
        }
    }
}

