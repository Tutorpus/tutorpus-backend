package com.tutorpus.tutorpus.schedule.dto;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Getter
public class AddScheduleDto {
    private LocalDate addDate;  //추가하려는 날짜
    private Long connectId; //학생과 선생님 연결 id
    private LocalTime startTime;    //과외 시작 시간
    private LocalTime endTime;  //과외 종료 시간
}
