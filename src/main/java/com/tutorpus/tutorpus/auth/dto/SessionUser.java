package com.tutorpus.tutorpus.auth.dto;

import com.tutorpus.tutorpus.member.entity.Member;
import lombok.Getter;

//TODO: 세션에 role 추가
@Getter
public class SessionUser {
    private String name;
    private String email;
    private String role;

    public SessionUser(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.role = member.getRole().getTitle();
    }
}