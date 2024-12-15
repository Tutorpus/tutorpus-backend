package com.tutorpus.tutorpus.student.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class StudentListDto {
    private Long connectId;
    private Long memberId;
    private String name;
    private String school;
    private String iconPath;
    private String subject;
    private String color;

    @Builder
    public StudentListDto(Long connectId, Long memberId, String name, String school, String iconPath, String subject, String color){
        this.connectId = connectId;
        this.memberId = memberId;
        this.name = name;
        this.school = school;
        this.iconPath = iconPath;
        this.subject = subject;
        this.color = color;
    }
}
