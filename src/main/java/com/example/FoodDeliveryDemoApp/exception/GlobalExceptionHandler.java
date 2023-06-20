package com.example.FoodDeliveryDemoApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

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

    @ExceptionHandler(ExternalServiceException.class)
    @ResponseBody
    public ResponseEntity<RestError> handleExternalServiceException(ExternalServiceException e) {
        RestError error = new RestError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public ResponseEntity<RestError> handleUnauthorizedException(UnauthorizedException e) {
        RestError error = new RestError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

}
