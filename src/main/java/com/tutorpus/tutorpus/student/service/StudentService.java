package com.tutorpus.tutorpus.student.service;

import com.tutorpus.tutorpus.member.entity.Member;
import com.tutorpus.tutorpus.member.repository.MemberRepository;
import com.tutorpus.tutorpus.student.dto.RequestStudentInfoDto;
import com.tutorpus.tutorpus.student.entity.Student;
import com.tutorpus.tutorpus.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Transactional
    public void studentInfo(RequestStudentInfoDto studentInfoDto, Member sessionMember) {
        Student student = Student.builder()
                .member(sessionMember)
                .school(studentInfoDto.getSchool())
                .icon("/image/path")    //TODO: icon path 정해지면 랜덤값으로 넣어주기
                .grade(studentInfoDto.getGrade())
                .color(studentInfoDto.getColor())
                .build();
        studentRepository.save(student);
    }
}
