package com.az.taskmasterbackend.exception;

import com.az.taskmasterbackend.model.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Bad credentials"));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadable() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Request body is missing or malformed"));
    }

    @ExceptionHandler(HttpMessageNotWritableException.class)
    public ResponseEntity<?> handleHttpMessageNotWritableException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Response body is malformed"));
    }
}