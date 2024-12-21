package com.tutorpus.tutorpus.member.controller;

import com.tutorpus.tutorpus.auth.LoginUser;
import com.tutorpus.tutorpus.auth.dto.SessionMember;
import com.tutorpus.tutorpus.member.dto.DevideDto;
import com.tutorpus.tutorpus.member.dto.LoginDto;
import com.tutorpus.tutorpus.member.dto.ReturnLoginDto;
import com.tutorpus.tutorpus.member.dto.SignupDto;
import com.tutorpus.tutorpus.member.entity.Member;
import com.tutorpus.tutorpus.member.entity.Role;
import com.tutorpus.tutorpus.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final HttpSession httpSession;

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

    //자체 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) throws Exception {
        ReturnLoginDto returnDto = memberService.login(loginDto);
        return ResponseEntity.ok(returnDto);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(@LoginUser Member member){
        memberService.logout(member);
        return ResponseEntity.ok("로그아웃 완료");
    }

    @GetMapping("/isLogin")
    public ResponseEntity<?> isLogin(@LoginUser Member member){
        return ResponseEntity.ok(member);
    }
}
