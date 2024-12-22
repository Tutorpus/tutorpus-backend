package com.tutorpus.tutorpus.schedule.controller;

import com.tutorpus.tutorpus.auth.LoginUser;
import com.tutorpus.tutorpus.member.entity.Member;
import com.tutorpus.tutorpus.schedule.dto.*;
import com.tutorpus.tutorpus.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
    @GetMapping("/{year}/{month}")
    public ResponseEntity<?> getSchedule(@LoginUser Member loginMember,
                                         @PathVariable("year") int year, @PathVariable("month") int month){
        Map<Long, ClassReturnDto> returnDto = scheduleService.realScheduleList(year, month, loginMember);
        return ResponseEntity.ok(returnDto);
    }

    //하루 스케쥴 조회
    @GetMapping("/detail/{year}/{month}/{day}")
    public ResponseEntity<?> getDailySchedule(@LoginUser Member loginMember,
                                              @PathVariable("year") int year, @PathVariable("month") int month, @PathVariable("day") int day){
        List<ClassDto> classDtos = scheduleService.scheduleDetail(year, month, day, loginMember);
        return ResponseEntity.ok(classDtos);
    }
    
    //한 학생의 전체 수업목록
    @GetMapping("/student/{connectId}/{year}/{month}")
    public ResponseEntity<?> getStudentSchedule(@LoginUser Member loginMember,
                                              @PathVariable("connectId") Long connectId, @PathVariable("year") int year, @PathVariable("month") int month){
        Map<LocalDate, StudentScheduleDto> returnDto = scheduleService.scheduleStudentDetail(year, month, loginMember, connectId);
        return ResponseEntity.ok(returnDto);
    }
}
