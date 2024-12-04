package com.tutorpus.tutorpus.schedule.controller;

import com.tutorpus.tutorpus.auth.LoginUser;
import com.tutorpus.tutorpus.connect.entity.ClassDay;
import com.tutorpus.tutorpus.connect.entity.Connect;
import com.tutorpus.tutorpus.connect.repository.ClassDayRepository;
import com.tutorpus.tutorpus.connect.repository.ConnectRepository;
import com.tutorpus.tutorpus.member.entity.Member;
import com.tutorpus.tutorpus.schedule.dto.AddScheduleDto;
import com.tutorpus.tutorpus.schedule.dto.DeleteScheduleDto;
import com.tutorpus.tutorpus.schedule.dto.EditScheduleDto;
import com.tutorpus.tutorpus.schedule.entity.Schedule;
import com.tutorpus.tutorpus.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final ConnectRepository connectRepository;

    //스케쥴 추가
    @PostMapping("/add")
    public ResponseEntity<?> addSchedule(@RequestBody AddScheduleDto addDto, @LoginUser Member loginMember){
        scheduleService.addSchedule(addDto, loginMember);
        return ResponseEntity.ok("스케쥴 추가 완료");
    }

    //스케쥴 삭제
    @PostMapping("/delete")
    public ResponseEntity<?> deleteSchedule(@RequestBody DeleteScheduleDto deleteDto, @LoginUser Member loginMember){
        scheduleService.deleteSchedule(deleteDto, loginMember);
        return ResponseEntity.ok("스케쥴 삭제 완료");
    }

    //스케쥴 수정
    @PostMapping("/edit")
    public ResponseEntity<?> editSchedule(@RequestBody EditScheduleDto editDto, @LoginUser Member loginMember){
        scheduleService.editSchedule(editDto, loginMember);
        return ResponseEntity.ok("스케쥴 수정 완료");
    }

    //스케쥴 조회
    @GetMapping()
    public ResponseEntity<?> getSchedule(@LoginUser Member member){
        List<LocalDate> localDates = scheduleService.realScheduleList(2024, 12, member);
        return ResponseEntity.ok(localDates);
    }
}
