package com.tutorpus.tutorpus.member.service;

import com.tutorpus.tutorpus.config.jwt.JwtTokenProvider;
import com.tutorpus.tutorpus.exception.CustomException;
import com.tutorpus.tutorpus.exception.ErrorCode;
import com.tutorpus.tutorpus.member.dto.DevideDto;
import com.tutorpus.tutorpus.member.dto.LoginDto;
import com.tutorpus.tutorpus.member.dto.ReturnLoginDto;
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
    private final JwtTokenProvider jwtTokenProvider;

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
    public ReturnLoginDto login(LoginDto loginDto) throws Exception {
        Member member = memberRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.NO_MEMBER));
        if (!passwordEncoder.matches(loginDto.getPassword(), member.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_INCORRECT);
        }

        // JWT 토큰 생성
        String jwt = jwtTokenProvider.createToken(member.getEmail(), member.getRole().toString());

        return new ReturnLoginDto(jwt, member.getName(), member.getEmail(), member.getRole().getTitle());
    }

    public void logout(Member member) {
        httpSession.removeAttribute("member");
        httpSession.invalidate();
    }
}
