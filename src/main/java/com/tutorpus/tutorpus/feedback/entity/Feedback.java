package com.tutorpus.tutorpus.feedback.entity;

import com.tutorpus.tutorpus.connect.entity.Connect;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //선생님과 학생 연결 id
    @ManyToOne
    @JoinColumn(name = "connect_id", referencedColumnName = "id", nullable = false)
    private Connect connect;

    //수업 일자 + 시작시간
    @Column(name = "class_date", nullable = false)
    private LocalDateTime classDate;

    //참여도
    @Column()
    private String participate;

    @Column(name = "participate_score", nullable = false)
    @Min(0)
    @Max(5)
    private int participateScore;

    //학습 적용도
    @Column()
    private String apply;

    @Column(name = "apply_score", nullable = false)
    @Min(0)
    @Max(5)
    private int applyScore;

    //숙제 완성도
    @Column(nullable = false)
    private String homework;

    @Column(name = "homework_score", nullable = false)
    @Min(0)
    @Max(5)
    private int homeworkScore;

    //피드백 등록시간
    @Column(name = "create_time", nullable = false)
    @CreationTimestamp
    private LocalDateTime createTime;

    @Builder
    public Feedback(Connect connect, LocalDateTime classDate, String participate, int participateScore, String apply, int applyScore, String homework, int homeworkScore) {
        this.connect = connect;
        this.classDate = classDate;
        this.participate = participate;
        this.participateScore = participateScore;
        this.apply = apply;
        this.applyScore = applyScore;
        this.homework = homework;
        this.homeworkScore = homeworkScore;
    }
}

