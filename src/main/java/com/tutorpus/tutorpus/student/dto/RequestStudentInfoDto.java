package com.tutorpus.tutorpus.student.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class RequestStudentInfoDto {
    private String school;
    private int grade;
    private String color;   //컬러코드. TODO: 추후 colorpicker을 사용하지 않는다면 dto 수정
}
