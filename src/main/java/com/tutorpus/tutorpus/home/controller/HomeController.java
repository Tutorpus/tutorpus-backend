package com.tutorpus.tutorpus.home.controller;

import com.tutorpus.tutorpus.auth.LoginUser;
import com.tutorpus.tutorpus.home.dto.HomeReturnDto;
import com.tutorpus.tutorpus.home.service.HomeService;
import com.tutorpus.tutorpus.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {
    private final HomeService homeService;

    @GetMapping()
    public ResponseEntity<?> comingSoon(@LoginUser Member member){
        HomeReturnDto dto = homeService.comingSoon(member);
        return ResponseEntity.ok(dto);
    }
}
