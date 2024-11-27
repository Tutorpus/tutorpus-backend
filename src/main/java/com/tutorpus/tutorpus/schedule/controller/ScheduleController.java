package com.tutorpus.tutorpus.schedule.controller;

import com.tutorpus.tutorpus.auth.LoginUser;
import com.tutorpus.tutorpus.member.entity.Member;
import com.tutorpus.tutorpus.schedule.dto.AddScheduleDto;
import com.tutorpus.tutorpus.schedule.entity.Schedule;
import com.tutorpus.tutorpus.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    //스케쥴 추가
    @PostMapping("/add")
    public ResponseEntity<?> addSchedule(@RequestBody AddScheduleDto addDto, @LoginUser Member loginMember){
        scheduleService.addSchedule(addDto, loginMember);
        return ResponseEntity.ok("스케쥴 추가 완료");
    }
}
