package com.tutorpus.tutorpus.member.controller;

import com.tutorpus.tutorpus.auth.dto.SessionMember;
import com.tutorpus.tutorpus.member.dto.DevideDto;
import com.tutorpus.tutorpus.member.dto.LoginDto;
import com.tutorpus.tutorpus.member.dto.SignupDto;
import com.tutorpus.tutorpus.member.entity.Role;
import com.tutorpus.tutorpus.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    //자체 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupDto signupDto) throws Exception {
        memberService.signup(signupDto);
        return ResponseEntity.ok("회원가입 성공");
    }
}
