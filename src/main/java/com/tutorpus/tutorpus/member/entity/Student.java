package com.tutorpus.tutorpus.member.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @Column(nullable = false)
    private String school;

    @Column(nullable = false)
    private int grade;

    //TODO: 컬러피커 - 단순 사용자에게 입력받는지? 내가 랜덤 돌리는지?
    @Column(nullable = false)
    private String color;

    //TODO: icon 사진 저장되어있는 경로 랜덤 저장
    @Column(nullable = false)
    private String icon;

    @Builder
    public Student(Member member, String school, int grade, String icon){
        this.member = member;
        this.school = school;
        this.grade = grade;
        this.icon = icon;
    }
}
