package org.sportify;

import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* *..controllers..*(..))")
    public Object logRequestAndResponse(ProceedingJoinPoint joinPoint) {
        logRequest(joinPoint);
        return logResponse(joinPoint);
    }

    private void logRequest(ProceedingJoinPoint joinPoint) {
        String endpoint = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        java.lang.reflect.Parameter[] parameters = methodSignature.getMethod().getParameters();
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String httpMethod = request.getMethod();
        logger.info("Endpoint: {}", endpoint);
        logger.info("Http Method: {}", httpMethod);
        if (args.length != 0) {
            for (int i = 0; i < parameters.length; i++) {
                Object arg = args[i];
                java.lang.reflect.Parameter parameter = parameters[i];

                if (parameter.isAnnotationPresent(RequestBody.class)) {
                    logger.info("Request Body: \n{}", JsonConverter.convertToJson(arg));
                }

                if (parameter.isAnnotationPresent(RequestHeader.class)) {
                    String headerName = parameter.getAnnotation(RequestHeader.class).value();
                    logger.info("Request Header:\n [{}]: {}", headerName, arg);
                }
            }
        }
    }

    @SneakyThrows
    private Object logResponse(ProceedingJoinPoint joinPoint) {
        Object result;
        try {
            result = joinPoint.proceed();
            if (result instanceof ResponseEntity<?> responseEntity) {
                logger.info("Response Status Code: {}", responseEntity.getStatusCode());
                logger.info("Response Body:\n {}", JsonConverter.convertToJson(responseEntity.getBody()));
            } else {
                logger.info("Response:\n {}", JsonConverter.convertToJson(result));
            }
        } catch (Exception ex) {
            throw ex;
        }
        return result;
    }

}
