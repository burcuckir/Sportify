package org.sportify;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final ErrorMessagesProvider errorMessagesProvider;

    public GlobalExceptionHandler(ErrorMessagesProvider errorMessagesProvider) {
        this.errorMessagesProvider = errorMessagesProvider;
    }

    @SneakyThrows
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse response = new ErrorResponse();
        String message = errorMessagesProvider.getMessageWithCode(ex.getMessage());
        response.setMessage(message);
        response.setCode(ex.getMessage());

        logger.error("Exception occurred: {}", ex.getClass().getSimpleName());
        logger.error("Error Message Code: {}", ex.getMessage());
        logger.error("HTTP Status: {}", HttpStatus.BAD_REQUEST);
        logger.error("Error Response: \n{}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
