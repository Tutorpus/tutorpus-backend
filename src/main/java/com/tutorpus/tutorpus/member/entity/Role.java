package com.tutorpus.tutorpus.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    GUEST("ROLE_TEACHER", "선생님"),
    USER("ROLE_STUDENT", "학생");

    private final String key;
    private final String title;
}
