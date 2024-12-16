package com.tutorpus.tutorpus.feedback.dto;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@Getter
public class AddFeedbackDto {
    private Long connectId;
    private LocalDateTime classDate;
    private String participate;
    private int participateScore;
    private String apply;
    private int applyScore;
    private String homework;
    private int homeworkScore;
}
