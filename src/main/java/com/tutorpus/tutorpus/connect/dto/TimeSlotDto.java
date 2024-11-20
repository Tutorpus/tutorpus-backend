package com.tutorpus.tutorpus.connect.dto;

import lombok.Data;
import lombok.Getter;

import java.time.LocalTime;

@Data
@Getter
public class TimeSlotDto {
    private String day;
    private LocalTime startTime;
    private LocalTime endTime;
}
