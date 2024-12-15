package com.tutorpus.tutorpus.homework.service;

import com.tutorpus.tutorpus.connect.entity.Connect;
import com.tutorpus.tutorpus.connect.repository.ConnectRepository;
import com.tutorpus.tutorpus.exception.CustomException;
import com.tutorpus.tutorpus.exception.ErrorCode;
import com.tutorpus.tutorpus.homework.dto.AddHomeworkDto;
import com.tutorpus.tutorpus.homework.dto.EditHomeworkDto;
import com.tutorpus.tutorpus.homework.dto.ReturnHomeworkDto;
import com.tutorpus.tutorpus.homework.entity.Homework;
import com.tutorpus.tutorpus.homework.repository.HomeworkRepository;
import com.tutorpus.tutorpus.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class HomeworkService {
    private final HomeworkRepository homeworkRepository;
    private final ConnectRepository connectRepository;

    @Transactional
    public void addHomework(Member member, AddHomeworkDto dto) {
        Connect connect = connectRepository.findById(dto.getConnectId())
                .orElseThrow(()-> new CustomException(ErrorCode.NO_CONNECT_ID));
        //connect의 선생님 정보와 member 정보가 일치하지 않는 경우
        if(!connect.getTeacher().getId().equals(member.getId()))
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

    @Transactional(readOnly = true)
    public List<ReturnHomeworkDto> getHomework(Member member, Long connectId, LocalDate date) {
        Connect connect = connectRepository.findById(connectId)
                .orElseThrow(()-> new CustomException(ErrorCode.NO_CONNECT_ID));
        if (!connect.getTeacher().getId().equals(member.getId()) &&
                !connect.getStudent().getId().equals(member.getId())) {
            throw new CustomException(ErrorCode.NO_CORRECT_CONNECT_ID);
        }

        //LocalDate(선택날짜) -> LocalDateTime 변환
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        List<ReturnHomeworkDto> returnDto = homeworkRepository.findByConnectIdAndDate(connectId, startOfDay, endOfDay).stream()
                .map(homework -> ReturnHomeworkDto.builder()
                        .homeworkId(homework.getId())
                        .title(homework.getTitle())
                        .content(homework.getContent())
                        .dueDate(homework.getEndDate())
                        .done(homework.getDone())
                        .build()
                )
                .collect(Collectors.toList());
        return returnDto;
    }

    @Transactional
    public void editHomework(Member member, EditHomeworkDto dto) {
        Homework homework = homeworkRepository.findById(dto.getHomeworkId())
                .orElseThrow(() -> new CustomException(ErrorCode.NO_HOMEWORK_ID));
        // 숙제와 연결된 connect의 선생님 정보와 member 정보가 일치하지 않는 경우
        if(!homework.getConnect().getTeacher().getId().equals(member.getId()))
            throw new CustomException(ErrorCode.NO_CORRECT_CONNECT_ID);

        homework.updateHomework(dto.getTitle(), dto.getContent(), dto.getEndDate());
    }

    @Transactional
    public void deleteHomework(Member member, Long homeworkId) {
        Homework homework = homeworkRepository.findById(homeworkId)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_HOMEWORK_ID));
        if(!homework.getConnect().getTeacher().getId().equals(member.getId()))
            throw new CustomException(ErrorCode.NO_CORRECT_CONNECT_ID);
        homeworkRepository.delete(homework);
    }
}
