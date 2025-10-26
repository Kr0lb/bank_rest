package com.example.bankcards.exception;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Comparator;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String responseMessage, Exception ex) {
        log.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());
        return ResponseEntity.status(status).body(new ErrorResponse(status.value(), ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .sorted(Comparator.comparing(FieldError::getField))
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return buildResponse(HttpStatus.BAD_REQUEST, message, ex);
    }

    @ExceptionHandler({UserNotFoundException.class, CardNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleEntityNotFound(Exception ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(Exception ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Неверные параметры запроса", ex);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(Exception ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Неверный формат запроса", ex);
    }

    @ExceptionHandler({AuthenticationException.class, ExpiredJwtException.class, UnauthorizedException.class})
    public ResponseEntity<ErrorResponse> handleUnauthorized(Exception ex) {
        return buildResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), ex);
    }

    @ExceptionHandler({
            AccessDeniedException.class,
            AuthorizationDeniedException.class
    })
    public ResponseEntity<ErrorResponse> handleAccessDenied(Exception ex) {
        return buildResponse(HttpStatus.FORBIDDEN, "Доступ запрещен", ex);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOtherExceptions(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Непредвиденная ошибка сервера", ex);
    }

}
