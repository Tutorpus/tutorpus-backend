package com.tutorpus.tutorpus.feedback.controller;

import com.tutorpus.tutorpus.auth.LoginUser;
import com.tutorpus.tutorpus.exception.CustomException;
import com.tutorpus.tutorpus.exception.ErrorCode;
import com.tutorpus.tutorpus.feedback.dto.AddFeedbackDto;
import com.tutorpus.tutorpus.feedback.service.FeedbackService;
import com.tutorpus.tutorpus.member.entity.Member;
import com.tutorpus.tutorpus.member.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feedback")
public class FeedbackController {
    private final FeedbackService feedbackService;

    @PostMapping("/add")
    public ResponseEntity<?> addFeedback(@LoginUser Member member, @RequestBody AddFeedbackDto dto){
        feedbackService.addFeedback(member, dto);
        return ResponseEntity.ok("피드백 등록 완료");
    }
}
