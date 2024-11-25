package com.tutorpus.tutorpus.config;

//import com.tutorpus.tutorpus.auth.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
//@EnableWebSecurity
public class SecurityConfig {

//    private final CustomOAuth2UserService customOAuth2UserService;

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
//        return http
//                //REST API 서버에서는 CSRF를 사용하지 않음. HTML 폼을 제출하는 방식에서는 보안을 위해 CSRF 유지
//                .csrf(csrf -> csrf.disable())
//                //h2-console 띄우기 위해
//                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
//                //경로별 접근 권한(로그인 이전은 전체 권한 부여)
//                .authorizeRequests(auth -> auth
//                        .requestMatchers("/", "/h2-console/**", "/member/login", "/member/signup", "/member/devide").permitAll()
////                        .requestMatchers("/api/v1/**").hasRole("TEACHER")
//                        .anyRequest().authenticated()
//                )
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
//                //로그아웃 성공 시 이동할 URL
//                .logout(logout -> logout
//                        .logoutSuccessUrl("/member/login")
//                )
//                .build();
//    }

    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
}