package com.tutorpus.tutorpus.student.controller;

import com.tutorpus.tutorpus.auth.LoginUser;
import com.tutorpus.tutorpus.auth.dto.SessionMember;
import com.tutorpus.tutorpus.exception.CustomException;
import com.tutorpus.tutorpus.exception.ErrorCode;
import com.tutorpus.tutorpus.member.entity.Member;
import com.tutorpus.tutorpus.member.entity.Role;
import com.tutorpus.tutorpus.student.dto.RequestStudentInfoDto;
import com.tutorpus.tutorpus.student.dto.StudentListDto;
import com.tutorpus.tutorpus.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/student")
@RestController
public class StudentController {
    private final StudentService studentService;

    //학생 추가정보
    @PostMapping("/info")
    public ResponseEntity<?> studentInfo(@RequestBody RequestStudentInfoDto studentInfoDto, @LoginUser Member member){
        if(member.getRole() == Role.TEACHER)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("선생님은 학생 추가정보 저장하지 않음.");
        studentService.studentInfo(studentInfoDto, member);
        return ResponseEntity.ok("학생 추가정보 저장 완료");
    }

    @GetMapping()
    public ResponseEntity<?> studentList(@LoginUser Member member){
        if(member.getRole() == Role.STUDENT)
            throw new CustomException(ErrorCode.NO_AUTHORITY_TO_STUDENT);
        List<StudentListDto> returnDto = studentService.getStudentList(member);
        return ResponseEntity.ok(returnDto);
    }

    @GetMapping("detail")
    public ResponseEntity<?> studentDetail(@LoginUser Member member){
        if(member.getRole() == Role.STUDENT)
            throw new CustomException(ErrorCode.NO_AUTHORITY_TO_STUDENT);
        List<StudentListDto> returnDto = studentService.getStudentList(member);
        return ResponseEntity.ok(returnDto);
    }
}
