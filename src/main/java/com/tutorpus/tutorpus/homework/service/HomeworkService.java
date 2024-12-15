package com.tutorpus.tutorpus.homework.service;

import com.tutorpus.tutorpus.connect.entity.Connect;
import com.tutorpus.tutorpus.connect.repository.ConnectRepository;
import com.tutorpus.tutorpus.exception.CustomException;
import com.tutorpus.tutorpus.exception.ErrorCode;
import com.tutorpus.tutorpus.homework.dto.addHomeworkDto;
import com.tutorpus.tutorpus.homework.entity.Homework;
import com.tutorpus.tutorpus.homework.repository.HomeworkRepository;
import com.tutorpus.tutorpus.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class HomeworkService {
    private final HomeworkRepository homeworkRepository;
    private final ConnectRepository connectRepository;

    @Transactional
    public void addHomework(Member member, addHomeworkDto dto) {
        Connect connect = connectRepository.findById(dto.getConnectId())
                .orElseThrow(()-> new CustomException(ErrorCode.NO_CONNECT_ID));
        //connect의 선생님 정보와 member 정보가 일치하지 않는 경우
        if(connect.getTeacher().getId() != member.getId())
            throw new CustomException(ErrorCode.NO_CORRECT_CONNECT_ID);

        Homework homework = Homework.builder()
                .connect(connect)
                .classDate(dto.getClassDate())
                .title(dto.getTitle())
                .content(dto.getContent())
                .endDate(dto.getEndDate())
                .done(false)
                .build();
        homeworkRepository.save(homework);
    }
}
