package com.tutorpus.tutorpus.exception.dto;

import com.tutorpus.tutorpus.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
//응답 반환 dto
public class ErrorResponseDto {
    private String code;        // ENUM 이름
    private String message;     // 에러 메시지
    private int status;         // HTTP 상태 코드

    public ErrorResponseDto(ErrorCode errorCode) {
        this.code = errorCode.name();
        this.message = errorCode.getMessage();
        this.status = errorCode.getStatus().value();
    }
}