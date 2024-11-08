package com.tutorpus.tutorpus.member.service;

import com.tutorpus.tutorpus.auth.dto.SessionMember;
import com.tutorpus.tutorpus.exception.DuplicateMemberException;
import com.tutorpus.tutorpus.exception.PasswordIncorrectException;
import com.tutorpus.tutorpus.member.dto.DevideDto;
import com.tutorpus.tutorpus.member.dto.LoginDto;
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

    @Transactional
    public void signup(SignupDto signupDto) throws Exception {
        //이미 회원이 존재하는 경우
        if (memberRepository.findByEmail(signupDto.getEmail()).isPresent()) {
            throw new DuplicateMemberException("이미 존재하는 이메일입니다.");
        }

        //role Enum으로 변경 및 비밀번호 암호화
        Role role = Role.StringToEnum(signupDto.getRole());
        signupDto.encodingPassword(passwordEncoder);

        Member member = Member.builder()
                .email(signupDto.getEmail())
                .name(signupDto.getName())
                .password(signupDto.getPassword())
                .role(role)
                .build();
        memberRepository.save(member);
    }

    @Transactional
    public SessionMember login(LoginDto loginDto) throws Exception {
        Member member = memberRepository.findByEmail(loginDto.getEmail()).orElse(null);
        if(member == null) return null;
        if (!passwordEncoder.matches(loginDto.getPassword(), member.getPassword()))
            throw new PasswordIncorrectException("비밀번호가 일치하지 않습니다.");

        //세션저장용 dto로 묶어서 세션에 저장
        SessionMember sessionMember = new SessionMember(member);
        httpSession.setAttribute("member", sessionMember);

        return sessionMember;
    }
}
