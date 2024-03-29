package com.example.LaptopKG.exception;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @Nullable
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        List<Map<String, String>> details;

        details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> Map.of(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        ApiError err = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                "Validation Errors" ,
                details);
        return ResponseEntityBuilder.build(err);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<Object> handleUserAlreadyExist(
            UserAlreadyExistException ex) {

        List<Map<String, String>> details = new ArrayList<>();
        details.add(Map.of(ex.getField(), ex.getMessage()));

        ApiError err = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                "Username already exists" ,
                details);

        return ResponseEntityBuilder.build(err);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUsernameNotFound(
            UserAlreadyExistException ex) {

        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ApiError err = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                "user not found" ,
                details);

        return ResponseEntityBuilder.build(err);
    }

    @ExceptionHandler(LaptopNotFoundException.class)
    public ResponseEntity<Object> handleLaptopNotFound(
            LaptopNotFoundException ex) {

        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ApiError err = new ApiError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND,
                "Laptop not found" ,
                details);

        return ResponseEntityBuilder.build(err);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFound(NotFoundException e){
        List<String> details = new ArrayList<>();
        details.add(e.getMessage());

        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND,
                "Not found",
                details
        );

        return ResponseEntityBuilder.build(error);
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<Object> handleAlreadyExist(AlreadyExistException e){
        List<String> details = new ArrayList<>();
        details.add(e.getMessage());

        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                "Already exists",
                details
        );

        return ResponseEntityBuilder.build(error);
    }

    @ExceptionHandler(TokenNotValidException.class)
    public ResponseEntity<Object> handleTokenNotValid(TokenNotValidException e){
        List<String> details = new ArrayList<>();
        details.add(e.getMessage());

        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                "Not valid",
                details
        );

        return ResponseEntityBuilder.build(error);
    }

}
