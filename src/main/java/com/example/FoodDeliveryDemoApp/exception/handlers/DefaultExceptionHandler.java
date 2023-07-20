package com.example.FoodDeliveryDemoApp.exception.handlers;

import com.example.FoodDeliveryDemoApp.exception.dto.RestError;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.concurrent.CompletionException;

@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public ResponseEntity<RestError> handleAuthenticationException(AuthenticationException ex) {
        RestError re = new RestError(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(re);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public ResponseEntity<RestError> handleAccessDeniedException(AccessDeniedException ex) {
        RestError re = new RestError(HttpStatus.FORBIDDEN.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(re);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseBody
    public ResponseEntity<RestError> handleExpiredJwtException(ExpiredJwtException ex) {
        RestError re = new RestError(HttpStatus.FORBIDDEN.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(re);
    }

/*    @ExceptionHandler(CompletionException.class)
    @ResponseBody
    public ResponseEntity<RestError> handleCompletionException(CompletionException e) {
        RestError re = new RestError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(re);
    }*/

    // Add more exception handlers for different exceptions as needed
}

