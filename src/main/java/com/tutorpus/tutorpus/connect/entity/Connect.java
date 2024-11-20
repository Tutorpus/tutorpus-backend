package com.tutorpus.tutorpus.connect.entity;

import com.tutorpus.tutorpus.member.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@Getter
@Entity
public class Connect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Member teacher;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Member student;

    //과목명
    @Column(nullable = false)
    private String subject;

    //요일
    @Column(name = "day_of_week", nullable = false)
    @Enumerated(EnumType.STRING)
    private Day day;

    //과외 시작 시간
    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    //과외 종료 시간
    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    //과외 시작 일자(오늘 날짜를 기준)
    @Column(name = "start_date", nullable = false)
    @CreationTimestamp
    private LocalDate startDate;

    @Builder
    public Connect(Member teacher, Member student, String subject, Day day, LocalTime startTime, LocalTime endTime){
        this.teacher = teacher;
        this.student = student;
        this.subject = subject;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
