package com.tutorpus.tutorpus.student.service;

import com.tutorpus.tutorpus.connect.entity.Connect;
import com.tutorpus.tutorpus.connect.repository.ConnectRepository;
import com.tutorpus.tutorpus.member.entity.Member;
import com.tutorpus.tutorpus.member.repository.MemberRepository;
import com.tutorpus.tutorpus.student.dto.RequestStudentInfoDto;
import com.tutorpus.tutorpus.student.dto.StudentListDto;
import com.tutorpus.tutorpus.student.entity.Student;
import com.tutorpus.tutorpus.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final ConnectRepository connectRepository;

    @Transactional
    public void studentInfo(RequestStudentInfoDto studentInfoDto, Member sessionMember) {
        Student student = Student.builder()
                .member(sessionMember)
                .school(studentInfoDto.getSchool())
                .icon("/image/path")    //TODO: icon path 정해지면 랜덤값으로 넣어주기
                .grade(studentInfoDto.getGrade())
                .build();
        studentRepository.save(student);
    }

    @Transactional(readOnly = true)
    public List<StudentListDto> getStudentList(Member member) {
        List<StudentListDto> returnDto = new ArrayList<>();   //반환 dto

        List<Connect> connectList = connectRepository.findByMemberId(member.getId());
        for (Connect connect : connectList){
            Student student = studentRepository.findByMemberId(connect.getStudent().getId());
            StudentListDto dto = StudentListDto.builder()
                    .connectId(connect.getId())
                    .memberId(connect.getStudent().getId())
                    .name(connect.getStudent().getName())
                    .school(student.getSchool() + student.getGrade())
                    .iconPath(student.getIcon())
                    .subject(connect.getSubject())
                    .color(connect.getColor())
                    .build();

            returnDto.add(dto);
        }

        return returnDto;
    }
}
