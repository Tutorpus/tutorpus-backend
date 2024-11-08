package com.tutorpus.tutorpus.member.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class LoginDto {
    private String email;
    private String password;
}
