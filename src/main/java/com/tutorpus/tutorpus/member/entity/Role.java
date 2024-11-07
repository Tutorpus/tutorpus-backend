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
}
