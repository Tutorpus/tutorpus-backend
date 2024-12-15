package com.tutorpus.tutorpus.homework.controller;

import com.tutorpus.tutorpus.auth.LoginUser;
import com.tutorpus.tutorpus.exception.CustomException;
import com.tutorpus.tutorpus.exception.ErrorCode;
import com.tutorpus.tutorpus.homework.dto.AddHomeworkDto;
import com.tutorpus.tutorpus.homework.dto.EditHomeworkDto;
import com.tutorpus.tutorpus.homework.dto.ReturnHomeworkDto;
import com.tutorpus.tutorpus.homework.service.HomeworkService;
import com.tutorpus.tutorpus.member.entity.Member;
import com.tutorpus.tutorpus.member.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/homework")
public class HomeworkController {
    private final HomeworkService homeworkService;

    @GetMapping("/{connectId}/{date}")
    public ResponseEntity<?> getHomework(@LoginUser Member member,
                                         @PathVariable("connectId") Long connectId, @PathVariable("date") LocalDate date){
        List<ReturnHomeworkDto> homework = homeworkService.getHomework(member, connectId, date);
        return ResponseEntity.ok(homework);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addHomework(@LoginUser Member member, @RequestBody AddHomeworkDto dto){
        if(member.getRole() == Role.STUDENT)
            throw new CustomException(ErrorCode.NO_AUTHORITY_TO_STUDENT);
        homeworkService.addHomework(member, dto);
        return ResponseEntity.ok("숙제 등록 완료");
    }

    //숙제 수정
    @PostMapping("/edit")
    public ResponseEntity<?> editHomework(@LoginUser Member member, @RequestBody EditHomeworkDto dto) {
        if (member.getRole() == Role.STUDENT) {
            throw new CustomException(ErrorCode.NO_AUTHORITY_TO_STUDENT);
        }
        homeworkService.editHomework(member, dto);
        return ResponseEntity.ok("숙제 수정 완료");
    }
}
