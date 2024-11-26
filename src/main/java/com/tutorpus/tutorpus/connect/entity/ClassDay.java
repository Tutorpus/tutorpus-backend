package com.tutorpus.tutorpus.connect.entity;

import com.tutorpus.tutorpus.connect.entity.Connect;
import com.tutorpus.tutorpus.connect.entity.Day;
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
public class ClassDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //선생님과 학생 연결 id
    @ManyToOne
    @JoinColumn(name = "connect_id", referencedColumnName = "id", nullable = false)
    private Connect connect;

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
    public ClassDay(Connect connect, Day day, LocalTime startTime, LocalTime endTime){
        this.connect = connect;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}

