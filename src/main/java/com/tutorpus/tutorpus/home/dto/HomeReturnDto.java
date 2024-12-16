package com.tutorpus.tutorpus.home.dto;

import com.tutorpus.tutorpus.homework.dto.ReturnHomeworkDto;
import com.tutorpus.tutorpus.schedule.dto.ClassDto;
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
public class HomeReturnDto {
    String name;
    LocalDate date;
    ClassDto classSoon;
    List<ReturnHomeworkDto> homework;
}
