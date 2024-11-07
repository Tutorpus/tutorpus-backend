package com.tutorpus.tutorpus.member.controller;

import com.tutorpus.tutorpus.member.dto.DevideDto;
import com.tutorpus.tutorpus.member.dto.SignupDto;
import com.tutorpus.tutorpus.member.entity.Role;
import com.tutorpus.tutorpus.member.repository.MemberRepository;
import com.tutorpus.tutorpus.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    //선생님인지 학생인지 구분
    @PostMapping("/devide")
    public ResponseEntity<?> getTeacherOrStudent(@RequestBody DevideDto devideDto){
        Role role = memberService.getTeacherOrStudent(devideDto);
        return ResponseEntity.ok(role + "역할이 세션에 저장되었습니다.");
    }
}
