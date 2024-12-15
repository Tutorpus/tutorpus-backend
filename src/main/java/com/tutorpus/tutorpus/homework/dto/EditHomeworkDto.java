package com.tutorpus.tutorpus.homework.dto;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@Getter
public class EditHomeworkDto {
    private Long HomeworkId;
    private String title;
    private String content;
    private LocalDateTime endDate;
}
