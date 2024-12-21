package com.tutorpus.tutorpus.exception;

import com.tutorpus.tutorpus.exception.dto.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomException(CustomException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(new ErrorResponseDto(ex.getErrorCode()));
    }
}


