package com.tutorpus.tutorpus.connect.service;

import com.tutorpus.tutorpus.connect.dto.ConnectRequestDto;
import com.tutorpus.tutorpus.connect.entity.ClassDay;
import com.tutorpus.tutorpus.connect.entity.Connect;
import com.tutorpus.tutorpus.connect.entity.Day;
import com.tutorpus.tutorpus.connect.repository.ClassDayRepository;
import com.tutorpus.tutorpus.connect.repository.ConnectRepository;
import com.tutorpus.tutorpus.exception.CustomException;
import com.tutorpus.tutorpus.exception.ErrorCode;
import com.tutorpus.tutorpus.member.entity.Member;
import com.tutorpus.tutorpus.member.entity.Role;
import com.tutorpus.tutorpus.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ConnectService {
    private final MemberRepository memberRepository;
    private final ConnectRepository connectRepository;
    private final ClassDayRepository classDayRepository;

    @Transactional
    public void teacherStudentConnect(ConnectRequestDto connectDto, Member teacher) {
        Member student = memberRepository.findByEmail(connectDto.getStudentEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.NO_MEMBER));
        //이미 connect가 존재하는 경우 409 에러
        if(connectRepository.findIfDuplicate(teacher.getId(), student.getId()) != null)
            throw new CustomException(ErrorCode.ALREADY_EXIST_CONNECT);
        //선생님이 학생만 저장할 수 있음. 그외의 경우 에러
        if (student.getRole() != Role.STUDENT) throw new CustomException(ErrorCode.NOT_STUDENT);
        if (teacher.getRole() != Role.TEACHER) throw new CustomException(ErrorCode.NOT_TEACHER);

        //선생님과 학생 연결정보 저장
        Connect connect = Connect.builder()
                .teacher(teacher)
                .student(student)
                .subject(connectDto.getSubject())
                .color(connectDto.getColor())
                .build();
        connectRepository.save(connect);

        //선생님과 학생 요일정보 저장
        connectDto.getTimeSlots().forEach(timeSlot -> {
            //요일 enum으로 변경
            Day day = Day.valueOf(timeSlot.getDay());
            ClassDay classDay = ClassDay.builder()
                    .connect(connect)
                    .day(day)
                    .startTime(timeSlot.getStartTime())
                    .endTime(timeSlot.getEndTime())
                    .build();
            classDayRepository.save(classDay);
        });
    }
}
