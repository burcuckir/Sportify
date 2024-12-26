package org.sportify.logging;

import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sportify.jsonconverter.JsonConverter;
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

        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append("Endpoint: ").append(endpoint).append("\n");
        logBuilder.append("Http Method: ").append(httpMethod).append("\n");
        if (args.length != 0) {
            for (int i = 0; i < parameters.length; i++) {
                Object arg = args[i];
                java.lang.reflect.Parameter parameter = parameters[i];

                if (parameter.isAnnotationPresent(RequestBody.class)) {
                    logBuilder.append("Request Body: \n").append(JsonConverter.convertToJson(arg)).append("\n");
                }

                if (parameter.isAnnotationPresent(RequestHeader.class)) {
                    String headerName = parameter.getAnnotation(RequestHeader.class).value();
                    logBuilder.append("Request Header: [").append(headerName).append("]: ").append(arg).append("\n");
                }
            }
        }
        logger.info("{}", logBuilder);
    }

    @SneakyThrows
    private Object logResponse(ProceedingJoinPoint joinPoint) {
        StringBuilder logBuilder = new StringBuilder();

        Object result;
        try {
            result = joinPoint.proceed();
            if (result instanceof ResponseEntity<?> responseEntity) {
                logBuilder.append("Response:{\n Status Code: ").append(responseEntity.getStatusCode()).append("\n");
                logBuilder.append("Body:\n").append(JsonConverter.convertToJson(responseEntity.getBody())).append("\n}");
            } else {
                logBuilder.append("Response:\n").append(JsonConverter.convertToJson(result)).append("\n");
            }
            logger.info("{}", logBuilder);
        } catch (Exception ex) {
            throw ex;
        }
        return result;
    }

}
