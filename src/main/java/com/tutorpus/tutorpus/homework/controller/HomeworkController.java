package com.tutorpus.tutorpus.homework.controller;

import com.tutorpus.tutorpus.auth.LoginUser;
import com.tutorpus.tutorpus.exception.CustomException;
import com.tutorpus.tutorpus.exception.ErrorCode;
import com.tutorpus.tutorpus.homework.dto.addHomeworkDto;
import com.tutorpus.tutorpus.homework.service.HomeworkService;
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
@RequestMapping("/homework")
public class HomeworkController {
    private final HomeworkService homeworkService;

    @PostMapping("/add")
    public ResponseEntity<?> addHomework(@LoginUser Member member, @RequestBody addHomeworkDto dto){
        if(member.getRole() == Role.STUDENT)
            throw new CustomException(ErrorCode.NO_AUTHORITY_TO_STUDENT);
        homeworkService.addHomework(member, dto);
        return ResponseEntity.ok("숙제 등록 완료");
    }
}
