package com.example.FoodDeliveryDemoApp.exception.handlers;

import com.example.FoodDeliveryDemoApp.exception.*;
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
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // CUSTOM EXCEPTIONS start

    @ExceptionHandler(CustomAccessDeniedException.class)
    @ResponseBody
    public ResponseEntity<RestError> handleAccessDeniedException(CustomAccessDeniedException e) {
        RestError error = new RestError(HttpStatus.FORBIDDEN.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(CustomBadRequestException.class)
    @ResponseBody
    public ResponseEntity<RestError> handleBadRequestException(CustomBadRequestException e) {
        RestError error = new RestError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(CustomNotFoundException.class)
    @ResponseBody
    public ResponseEntity<RestError> handleNotFoundException(CustomNotFoundException e) {
        RestError error = new RestError(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(CustomUnauthorizedException.class)
    @ResponseBody
    public ResponseEntity<RestError> handleUnauthorizedException(CustomUnauthorizedException e) {
        RestError error = new RestError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(CustomInternalServerError.class)
    @ResponseBody
    public ResponseEntity<RestError> handleInternalServiceException(CustomInternalServerError e) {
        RestError error = new RestError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    // CUSTOM EXCEPTIONS end

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

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    @ResponseBody
    public ResponseEntity<RestError> handleSpringSecurityAccessDeniedException(
            org.springframework.security.access.AccessDeniedException ex) {
        RestError re = new RestError(HttpStatus.FORBIDDEN.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(re);
    }


    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public ResponseEntity<RestError> handleUnauthorizedException(UnauthorizedException e) {
        RestError error = new RestError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseBody
    public ResponseEntity<RestError> handleExpiredJwtException(ExpiredJwtException ex) {
        RestError re = new RestError(HttpStatus.FORBIDDEN.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(re);
    }

    @ExceptionHandler(ExternalServiceException.class)
    @ResponseBody
    public ResponseEntity<RestError> handleExternalServiceException(ExternalServiceException e) {
        RestError error = new RestError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    // fixme
    // ex.message() is only for testing purposes
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<RestError> handleAllOtherExceptions(Exception ex) {
        logger.error("An unexpected error occurred", ex); // Log the actual error details for your own review
/*        String error = "An unexpected error occurred. Please contact the maintainer of the site";
        RestError re = new RestError(HttpStatus.INTERNAL_SERVER_ERROR.value(), error);*/
        RestError re = new RestError(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(re);
    }

    @ExceptionHandler(CompletionException.class)
    @ResponseBody
    public ResponseEntity<RestError> handleCompletionException(CompletionException e) {
        Throwable cause = e.getCause();
        if (cause instanceof CustomAccessDeniedException) {
            return handleAccessDeniedException((CustomAccessDeniedException) cause);
        } else if (cause instanceof CustomBadRequestException) {
            return handleBadRequestException((CustomBadRequestException) cause);
        } else if (cause instanceof CustomNotFoundException) {
            return handleNotFoundException((CustomNotFoundException) cause);
        } else if (cause instanceof CustomUnauthorizedException) {
            return handleUnauthorizedException((CustomUnauthorizedException) cause);
        } else if (cause instanceof ExternalServiceException) {
            return handleExternalServiceException((ExternalServiceException) cause);
        } else if (cause instanceof CustomInternalServerError) {
            return handleInternalServiceException((CustomInternalServerError) cause);
        } else if (cause instanceof UnauthorizedException) {
            return handleUnauthorizedException((UnauthorizedException) cause);
        } else {
            RestError re = new RestError(HttpStatus.INTERNAL_SERVER_ERROR.value(), cause.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(re);
        }
    }

}
