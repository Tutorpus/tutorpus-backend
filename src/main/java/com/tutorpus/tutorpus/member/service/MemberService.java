package com.tutorpus.tutorpus.member.service;

import com.tutorpus.tutorpus.member.dto.DevideDto;
import com.tutorpus.tutorpus.member.dto.SignupDto;
import com.tutorpus.tutorpus.member.entity.Member;
import com.tutorpus.tutorpus.member.entity.Role;
import com.tutorpus.tutorpus.member.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final HttpSession httpSession;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Role getTeacherOrStudent(DevideDto devideDto) {
        //role 정보 세션에 저장
        String role = devideDto.getRole();
        return Role.StringToEnum(role);
    }
}
