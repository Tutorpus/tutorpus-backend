package com.tutorpus.tutorpus.schedule.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Getter
@NoArgsConstructor
public class ClassDto {
    Long connectId; //서버개발용. 프론트 필요x
    String studentName; //선생님 스케쥴용
    String subject; //학생 스케쥴용
    String color;
    LocalTime startTime;
    LocalTime endTime;

    @Builder
    public ClassDto(Long connectId, String studentName, String subject, String color, LocalTime startTime, LocalTime endTime){
        this.connectId = connectId;
        this.studentName =  studentName;
        this.subject = subject;
        this.color = color;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
