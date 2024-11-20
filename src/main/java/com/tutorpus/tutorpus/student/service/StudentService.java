package com.tutorpus.tutorpus.student.service;

import com.tutorpus.tutorpus.auth.dto.SessionMember;
import com.tutorpus.tutorpus.exception.NoMemberException;
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
    private final MemberRepository memberRepository;

    @Transactional
    public void studentInfo(RequestStudentInfoDto studentInfoDto, SessionMember sessionMember) {
        Member member = memberRepository.findByEmail(sessionMember.getEmail())
                .orElseThrow(() -> new NoMemberException("해당 회원이 존재하지 않습니다."));
        System.out.println("현재 이메일 " + sessionMember.getEmail());

        Student student = Student.builder()
                .member(member)
                .school(studentInfoDto.getSchool())
                .icon("/image/path")    //TODO: icon path 정해지면 랜덤값으로 넣어주기
                .grade(studentInfoDto.getGrade())
                .build();
        studentRepository.save(student);
    }
}
