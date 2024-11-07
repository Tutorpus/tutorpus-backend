package com.tutorpus.tutorpus.member.dto;

import lombok.Data;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@Getter
public class SignupDto {
    private String name;
    private String email;
    private String password;
    private String role;    //memberService에서 Role enum으로 변경

    public void encodingPassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }
}
