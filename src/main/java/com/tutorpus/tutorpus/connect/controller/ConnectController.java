package com.tutorpus.tutorpus.connect.controller;

import com.tutorpus.tutorpus.auth.LoginUser;
import com.tutorpus.tutorpus.auth.dto.SessionMember;
import com.tutorpus.tutorpus.connect.dto.ConnectRequestDto;
import com.tutorpus.tutorpus.connect.service.ConnectService;
import com.tutorpus.tutorpus.member.entity.Member;
import com.tutorpus.tutorpus.member.entity.Role;
import com.tutorpus.tutorpus.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/connect")
@RestController
public class ConnectController {
    private final ConnectService connectService;

    @PostMapping()
    public ResponseEntity<?> teacherStudentConnect(@RequestBody ConnectRequestDto connectDto, @LoginUser Member member){
        connectService.teacherStudentConnect(connectDto, member);
        return ResponseEntity.ok("선생님과 학생 연결 완료");
    }
}
