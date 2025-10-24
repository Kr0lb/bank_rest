package com.example.bankcards.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Comparator;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<String> buildResponse(HttpStatus status, String responseMessage, Exception ex) {
        log.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
        return ResponseEntity.status(status).body(responseMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .sorted(Comparator.comparing(FieldError::getField))
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return buildResponse(HttpStatus.BAD_REQUEST, message, ex);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFound(EntityNotFoundException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Сущность не найдена", ex);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleUnauthorized(AuthenticationException ex) {
        return buildResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), ex);
    }

    @ExceptionHandler({
            AccessDeniedException.class,
            AuthorizationDeniedException.class
    })
    public ResponseEntity<String> handleAccessDenied(Exception ex) {
        return buildResponse(HttpStatus.FORBIDDEN, "Доступ запрещен", ex);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleOtherExceptions(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Непредвиденная ошибка сервера", ex);
    }

}
