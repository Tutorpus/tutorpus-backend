package com.tutorpus.tutorpus.feedback.dto;

import com.tutorpus.tutorpus.feedback.entity.Feedback;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReturnFeedbackDto {
    private Long feedbackId;
    private LocalDateTime classDate;
    private String participate;
    private int participateScore;
    private String apply;
    private int applyScore;
    private String homework;
    private int homeworkScore;

    public static ReturnFeedbackDto FeedbackToDto(Feedback feedback){
        return new ReturnFeedbackDto(
                feedback.getId(),
                feedback.getClassDate(),
                feedback.getParticipate(),
                feedback.getParticipateScore(),
                feedback.getApply(),
                feedback.getApplyScore(),
                feedback.getHomework(),
                feedback.getHomeworkScore()
        );
    }
}
