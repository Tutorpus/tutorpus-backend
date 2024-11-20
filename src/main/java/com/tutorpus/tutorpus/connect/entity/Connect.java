package com.tutorpus.tutorpus.connect.entity;

import com.tutorpus.tutorpus.member.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member teacher;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member student;

    //과목명
    @Column(nullable = false)
    private String subject;

    //요일
    @Column(nullable = false)
    private String day;

    //과외 시작 시간
    @Column(nullable = false)
    private LocalTime start_time;

    //과외 종료 시간
    @Column(nullable = false)
    private LocalTime end_time;

    //과외 시작 일자(오늘 날짜를 기준)
    @Column(nullable = false)
    private LocalDate start_date;

    @Builder
    public Connect(Member teacher, Member student, String subject, String day, LocalTime start_time, LocalTime end_time, LocalDate start_date){
        this.teacher = teacher;
        this.student = student;
        this.subject = subject;
        this.day = day;
        this.start_time = start_time;
        this.end_time = end_time;
        this.start_date = LocalDate.now();
    }
}
