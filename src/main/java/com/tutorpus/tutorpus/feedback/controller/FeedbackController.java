package com.tutorpus.tutorpus.feedback.controller;

import com.tutorpus.tutorpus.auth.LoginUser;
import com.tutorpus.tutorpus.feedback.dto.AddFeedbackDto;
import com.tutorpus.tutorpus.feedback.dto.ReturnFeedbackDto;
import com.tutorpus.tutorpus.feedback.service.FeedbackService;
import com.tutorpus.tutorpus.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

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

    @GetMapping("/{connectId}/{date}/{startTime}")
    public ResponseEntity<?> getHomework(@LoginUser Member member,
                                         @PathVariable("connectId") Long connectId, @PathVariable("date") LocalDate date, @PathVariable("startTime") LocalTime startTime){
        ReturnFeedbackDto feedback = feedbackService.getFeedback(member, connectId, date, startTime);
        return ResponseEntity.ok(feedback);
    }
}
