package com.tutorpus.tutorpus.homework.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@NoArgsConstructor
public class ReturnHomeworkDto {
    private Long homeworkId;
    private String title;
    private String content;
    private LocalDateTime dueDate;
    private Boolean done;

    @Builder
    public ReturnHomeworkDto(Long homeworkId, String title, String content, LocalDateTime dueDate, Boolean done){
        this.homeworkId = homeworkId;
        this.title = title;
        this.content = content;
        this.dueDate = dueDate;
        this.done = done;
    }
}
