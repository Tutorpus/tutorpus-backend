package com.tutorpus.tutorpus.homework.entity;

import com.tutorpus.tutorpus.connect.entity.Connect;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
public class Homework {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //선생님과 학생 연결 id
    @ManyToOne
    @JoinColumn(name = "connect_id", referencedColumnName = "id", nullable = false)
    private Connect connect;

    //날짜 + 수업 시작시간
    @Column(name = "class_date", nullable = false)
    private LocalDateTime classDate;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    //숙제 마감일자
    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    //숙제 마감일자
    @Column(nullable = false)
    private Boolean done;

    //숙제 등록시간
    @Column(name = "create_time", nullable = false)
    @CreationTimestamp
    private LocalDateTime createTime;

    @Builder
    public Homework(Connect connect, LocalDateTime classDate, String title, String content, LocalDateTime endDate, Boolean done) {
        this.connect = connect;
        this.classDate = classDate;
        this.title = title;
        this.content = content;
        this.endDate = endDate;
        this.done = done;
    }

    public void updateHomework(String title, String content, LocalDateTime endDate) {
        this.title = title;
        this.content = content;
        this.endDate = endDate;
    }
}
