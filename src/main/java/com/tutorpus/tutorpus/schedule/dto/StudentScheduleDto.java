package com.tutorpus.tutorpus.schedule.dto;

import com.tutorpus.tutorpus.connect.entity.Day;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@Getter
@NoArgsConstructor
public class StudentScheduleDto {
    DayOfWeek day;
    LocalTime startTime;
    LocalTime endTime;

    @Builder
    public StudentScheduleDto(DayOfWeek day, LocalTime startTime, LocalTime endTime){
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
