package com.tutorpus.tutorpus.connect.service;

import com.tutorpus.tutorpus.connect.dto.ConnectRequestDto;
import com.tutorpus.tutorpus.connect.entity.Connect;
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
            Connect connect = Connect.builder()
                    .teacher(teacher)
                    .student(student)
                    .day(timeSlot.getDay())
                    .start_time(timeSlot.getStartTime())
                    .end_time(timeSlot.getEndTime())
                    .build();

            connectRepository.save(connect);
        });
    }
}
