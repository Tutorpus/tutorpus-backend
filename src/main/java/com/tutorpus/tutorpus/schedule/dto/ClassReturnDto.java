package com.tutorpus.tutorpus.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClassReturnDto {
    private String color;
    private List<LocalDate> dates;
}
