package com.example.back_end.exceptions;

import com.example.back_end.util.APIResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<APIResponse<String>> handleGenericException(Exception e) {    //handle all exception here

        System.err.println("An error occurred: " + e.getMessage());
        return new ResponseEntity<>(new APIResponse<String>(
                500,
                "An unexpected error occurred: " + e.getMessage(),
                null),
                HttpStatus.INTERNAL_SERVER_ERROR
        );

        // You can also return a custom response or throw a specific exception
        // For example, you can create a custom error response class and return it
        // throw new CustomException("Custom error message", e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<Object>>  handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> { // Loop through each field error and add it to the errors map
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return new ResponseEntity<>(new APIResponse<>( // Create a new APIResponse object with the validation errors
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                errors
        ),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public APIResponse<String> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return new APIResponse<>(404, e.getMessage(), null);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public APIResponse<String> handleBadCredentialsException(BadCredentialsException e) {
        return new APIResponse<>(401, e.getMessage(), null);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public APIResponse<String> handleExpiredJwtException(ExpiredJwtException e) {
        return new APIResponse<>(401, e.getMessage(), null);
    }
}
