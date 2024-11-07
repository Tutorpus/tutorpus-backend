package com.tutorpus.tutorpus.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    TEACHER("ROLE_TEACHER", "teacher"),
    STUDENT("ROLE_STUDENT", "student");

    private final String key;
    private final String title;

    public static Role StringToEnum(String role) {
        try {
            return Role.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(role + "는 유효하지 않은 역할입니다.");
        }
    }
}
