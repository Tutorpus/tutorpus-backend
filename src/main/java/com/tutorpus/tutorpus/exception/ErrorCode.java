package com.tutorpus.tutorpus.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    // 회원 관련
    EMAIL_ALREADY_EXISTS("이미 존재하는 이메일입니다.", HttpStatus.CONFLICT),
    PASSWORD_INCORRECT("비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED),
    NO_MEMBER("존재하는 회원이 없습니다.", HttpStatus.BAD_REQUEST),
    NOT_TEACHER("선생님만 학생을 저장할 수 있습니다.", HttpStatus.BAD_REQUEST),
    NOT_STUDENT("학생이 아닙니다.", HttpStatus.BAD_REQUEST),
    NO_AUTHORITY_TO_STUDENT("학생은 권한이 없습니다.", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED),

    //스케쥴 관련
    ALREADY_EXIST_CONNECT("이미 해당 학생과 연결되어있습니다.", HttpStatus.CONFLICT),
    NO_CONNECT_ID("존재하는 선생님과 학생 연결 id가 없습니다.", HttpStatus.NOT_FOUND),
    CANNOT_ADD_SCHEDULE("과외 시작시간과 종료시간이 없습니다.", HttpStatus.BAD_REQUEST),
    NO_EXIST_DAY("존재하지 않는 요일입니다.", HttpStatus.BAD_REQUEST),
    NO_CORRECT_CONNECT_ID("해당 연결id와 로그인 정보가 맞지 않습니다.", HttpStatus.BAD_REQUEST),
    
    //숙제 및 피드백 관련
    NO_HOMEWORK_ID("해당 숙제id가 없습니다.", HttpStatus.BAD_REQUEST),
    FEEDBACK_ALREADY_EXIST("해당 수업의 피드백이 이미 존재합니다.", HttpStatus.CONFLICT),

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

