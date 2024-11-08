package com.tutorpus.tutorpus.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 중복 회원 처리 예외
    @ExceptionHandler(DuplicateMemberException.class)
    @ResponseStatus(HttpStatus.CONFLICT)  // 409 Conflict 상태 코드 반환
    @ResponseBody
    public String handleDuplicateMemberException(DuplicateMemberException ex) {
        return ex.getMessage();  // 예외 메시지 반환
    }
}

