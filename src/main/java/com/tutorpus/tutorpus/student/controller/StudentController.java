package com.tutorpus.tutorpus.student.controller;

import com.tutorpus.tutorpus.auth.LoginUser;
import com.tutorpus.tutorpus.auth.dto.SessionMember;
import com.tutorpus.tutorpus.member.entity.Member;
import com.tutorpus.tutorpus.student.dto.RequestStudentInfoDto;
import com.tutorpus.tutorpus.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/student")
@RestController
public class StudentController {
    private final StudentService studentService;

    @PostMapping("/info")
    public ResponseEntity<?> studentInfo(@RequestBody RequestStudentInfoDto studentInfoDto, @LoginUser Member member){
        if(member.getRole().equals("TEACHER"))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("선생님은 학생 추가정보 저장하지 않음.");
        studentService.studentInfo(studentInfoDto, member);
        return ResponseEntity.ok("학생 추가정보 저장 완료");
    }
}
