package org.sportify;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
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

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* *..controllers..*(..))")
    public Object logRequestAndResponse(ProceedingJoinPoint joinPoint) throws Throwable {
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
                    logger.info("Request Body: \n{}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(arg));
                }

                if (parameter.isAnnotationPresent(RequestHeader.class)) {
                    String headerName = parameter.getAnnotation(RequestHeader.class).value();
                    logger.info("Request Header:\n [{}]: {}", headerName, arg);
                }
            }
        }

        Object result;
        try {
            result = joinPoint.proceed();
            if (result instanceof ResponseEntity<?> responseEntity) {
                logger.info("Response Status Code: {}", responseEntity.getStatusCode());
                logger.info("Response Body:\n {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseEntity.getBody()));
            } else {
                logger.info("Response:\n {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
            }
        } catch (Exception ex) {
            throw ex;
        }
        return result;
    }

}
