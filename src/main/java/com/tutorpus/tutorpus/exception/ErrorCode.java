package com.tutorpus.tutorpus.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // 학생 정보 저장 관련 에러
    TEACHER_CANNOT_SAVE_STUDENT_INFO("선생님은 학생 정보를 저장할 수 없습니다.", HttpStatus.BAD_REQUEST),

    // 회원 관련
    EMAIL_ALREADY_EXISTS("이미 존재하는 이메일입니다.", HttpStatus.CONFLICT),
    PASSWORD_INCORRECT("비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED),

    // 공통 에러
    INTERNAL_SERVER_ERROR("서버 에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String message;
    private final HttpStatus status;

    ErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}

