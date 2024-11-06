package com.tutorpus.tutorpus.global.config.dto;

import com.tutorpus.tutorpus.member.entity.Member;
import lombok.Getter;

@Getter
public class SessionUser {
    private String name;
    private String email;

    public SessionUser(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
    }
}