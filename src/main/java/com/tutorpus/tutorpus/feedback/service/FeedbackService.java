package com.tutorpus.tutorpus.feedback.service;

import com.tutorpus.tutorpus.connect.entity.Connect;
import com.tutorpus.tutorpus.connect.repository.ConnectRepository;
import com.tutorpus.tutorpus.exception.CustomException;
import com.tutorpus.tutorpus.exception.ErrorCode;
import com.tutorpus.tutorpus.feedback.dto.AddFeedbackDto;
import com.tutorpus.tutorpus.feedback.dto.ReturnFeedbackDto;
import com.tutorpus.tutorpus.feedback.entity.Feedback;
import com.tutorpus.tutorpus.feedback.repository.FeedbackRepository;
import com.tutorpus.tutorpus.homework.dto.ReturnHomeworkDto;
import com.tutorpus.tutorpus.member.entity.Member;
import com.tutorpus.tutorpus.member.entity.Role;
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
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final ConnectRepository connectRepository;

    @Transactional
    public void addFeedback(Member member, AddFeedbackDto dto) {
        if(member.getRole() == Role.STUDENT)
            throw new CustomException(ErrorCode.NO_AUTHORITY_TO_STUDENT);
        Connect connect = connectRepository.findById(dto.getConnectId())
                .orElseThrow(()-> new CustomException(ErrorCode.NO_CONNECT_ID));
        //connect의 선생님 정보와 member 정보가 일치하지 않는 경우
        if(!connect.getTeacher().getId().equals(member.getId()))
            throw new CustomException(ErrorCode.NO_CORRECT_CONNECT_ID);
        //한 수업에 하나의 피드백만 가능. 이미 피드백이 있는 경우 에러.
        if(feedbackRepository.findByClassDate(dto.getClassDate()) != null)
            throw new CustomException(ErrorCode.FEEDBACK_ALREADY_EXIST);

        Feedback feedback = Feedback.builder()
                .connect(connect)
                .classDate(dto.getClassDate())
                .participate(dto.getParticipate())
                .participateScore(dto.getParticipateScore())
                .apply(dto.getApply())
                .applyScore(dto.getApplyScore())
                .homework(dto.getHomework())
                .homeworkScore(dto.getHomeworkScore())
                .build();
        feedbackRepository.save(feedback);
    }
}
