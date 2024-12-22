package com.tutorpus.tutorpus.schedule.dto;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Getter
public class DeleteScheduleDto {
    private LocalDateTime deleteDate;  //삭제하려는 날짜
    private Long connectId; //학생과 선생님 연결 id
}
