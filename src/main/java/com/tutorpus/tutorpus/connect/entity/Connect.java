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
    @JoinColumn(name = "teacher_id", referencedColumnName = "id", nullable = false)
    private Member teacher;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false)
    private Member student;

    //과목명
    @Column(nullable = false)
    private String subject;

    @Builder
    public Connect(Member teacher, Member student, String subject){
        this.teacher = teacher;
        this.student = student;
        this.subject = subject;
    }
}
