package com.tutorpus.tutorpus.member.service;

import com.tutorpus.tutorpus.member.dto.DevideDto;
import com.tutorpus.tutorpus.member.entity.Role;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final HttpSession httpSession;

    public String getTeacherOrStudent(DevideDto devideDto) {
        //role 정보 세션에 저장
        String role = devideDto.getRole();
        if (role.equals("student")){
            Role preRole = Role.STUDENT;
            httpSession.setAttribute("preRole", preRole);  // 역할 정보를 세션에 저장
            return role;
        } else if (role.equals("teacher")){
            Role preRole = Role.TEACHER;
            httpSession.setAttribute("preRole", preRole);  // 역할 정보를 세션에 저장
            System.out.println("세션 저장");
            return role;
        }
        return null;
    }
}
