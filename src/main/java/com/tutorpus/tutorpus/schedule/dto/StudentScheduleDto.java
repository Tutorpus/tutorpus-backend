package com.tutorpus.tutorpus.schedule.dto;

import com.tutorpus.tutorpus.connect.entity.ClassDay;
import com.tutorpus.tutorpus.connect.entity.Day;
import com.tutorpus.tutorpus.schedule.entity.Schedule;
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

    public static StudentScheduleDto scheduleToDto(Schedule schedule){
        StudentScheduleDto dto = new StudentScheduleDto();
        dto.day = schedule.getEditDate().getDayOfWeek();
        dto.startTime = schedule.getStartTime();
        dto.endTime = schedule.getEndTime();
        return dto;
    }

    public static StudentScheduleDto classDayToDto(ClassDay classDay){
        StudentScheduleDto dto = new StudentScheduleDto();
        dto.day = classDay.getDay().getDayOfWeek();
        dto.startTime = classDay.getStartTime();
        dto.endTime = classDay.getEndTime();
        return dto;
    }
}
