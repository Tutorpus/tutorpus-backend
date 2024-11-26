package com.tutorpus.tutorpus.schedule.entity;

import com.tutorpus.tutorpus.connect.entity.Connect;
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
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //선생님과 학생 연결 id
    @ManyToOne
    @JoinColumn(name = "connect_id", referencedColumnName = "id", nullable = false)
    private Connect connect;

    //수정&삭제 일자
    @Column(name = "edit_date", nullable = false)
    @CreationTimestamp
    private LocalDate editDate;

    //변경 후 일자 - 삭제의 경우 null 가능
    @Column(name = "add_date")
    @CreationTimestamp
    private LocalDate addDate;

    //과외 시작 시간 - 삭제의 경우 null 가능
    @Column(name = "start_time")
    private LocalTime startTime;

    //과외 종료 시간 - 삭제의 경우 null 가능
    @Column(name = "end_time")
    private LocalTime endTime;

    //삭제(1), 추가(0)
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Builder
    public Schedule(Connect connect, Boolean isDeleted, LocalDate addDate, LocalTime startTime, LocalTime endTime) {
        this.connect = connect;
        this.isDeleted = isDeleted;
        this.addDate = addDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
