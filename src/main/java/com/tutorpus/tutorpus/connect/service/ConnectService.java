package com.tutorpus.tutorpus.connect.service;

import com.tutorpus.tutorpus.connect.dto.ConnectRequestDto;
import com.tutorpus.tutorpus.connect.entity.Connect;
import com.tutorpus.tutorpus.connect.entity.Day;
import com.tutorpus.tutorpus.connect.repository.ConnectRepository;
import com.tutorpus.tutorpus.member.entity.Member;
import com.tutorpus.tutorpus.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ConnectService {
    private final MemberRepository memberRepository;
    private final ConnectRepository connectRepository;

    @Transactional
    public void teacherStudentConnect(ConnectRequestDto connectDto, Member teacher) {
        //학생
        Member student = memberRepository.findByEmail(connectDto.getStudentEmail()).orElse(null);

        //선생님과 학생 연결 정보 저장
        connectDto.getTimeSlots().forEach(timeSlot -> {
            //요일 enum으로 변경
            Day day = Day.valueOf(timeSlot.getDay());

            Connect connect = Connect.builder()
                    .teacher(teacher)
                    .student(student)
                    .day(day)
                    .startTime(timeSlot.getStartTime())
                    .endTime(timeSlot.getEndTime())
                    .build();

            connectRepository.save(connect);
        });
    }
}