package com.tutorpus.tutorpus.member.service;

import com.tutorpus.tutorpus.exception.CustomException;
import com.tutorpus.tutorpus.exception.ErrorCode;
import com.tutorpus.tutorpus.member.dto.DevideDto;
import com.tutorpus.tutorpus.member.dto.LoginDto;
import com.tutorpus.tutorpus.member.dto.SignupDto;
import com.tutorpus.tutorpus.member.entity.Member;
import com.tutorpus.tutorpus.member.entity.Role;
import com.tutorpus.tutorpus.member.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final HttpSession httpSession;
    private final PasswordEncoder passwordEncoder;
//    private final AuthenticationManager authenticationManager;

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
            throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS);
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
    public Member login(LoginDto loginDto) throws Exception {
        Member member = memberRepository.findByEmail(loginDto.getEmail()).orElse(null);
        if(member == null) return null;

        if (!passwordEncoder.matches(loginDto.getPassword(), member.getPassword()))
            throw new CustomException(ErrorCode.PASSWORD_INCORRECT);

//        // 사용자 정보 기반으로 Authentication 객체 생성
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
//        );
//        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 세션에 사용자 정보 저장
        //SessionMember sessionMember = new SessionMember(member);  // 세션에 저장할 사용자 정보
        httpSession.setAttribute("member", member);

        return member;
    }

    public void logout(Member member) {
        httpSession.removeAttribute("member");
        httpSession.invalidate();
    }
}
