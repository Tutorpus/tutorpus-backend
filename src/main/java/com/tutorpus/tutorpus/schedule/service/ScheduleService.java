package com.tutorpus.tutorpus.schedule.service;

import com.tutorpus.tutorpus.connect.entity.Connect;
import com.tutorpus.tutorpus.connect.repository.ConnectRepository;
import com.tutorpus.tutorpus.exception.CustomException;
import com.tutorpus.tutorpus.exception.ErrorCode;
import com.tutorpus.tutorpus.member.entity.Member;
import com.tutorpus.tutorpus.member.entity.Role;
import com.tutorpus.tutorpus.schedule.dto.AddScheduleDto;
import com.tutorpus.tutorpus.schedule.dto.DeleteScheduleDto;
import com.tutorpus.tutorpus.schedule.entity.Schedule;
import com.tutorpus.tutorpus.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ConnectRepository connectRepository;

    @Transactional
    public void addSchedule(AddScheduleDto addDto, Member loginMember) {
        //선생님만 일정 추가 가능
        if (loginMember.getRole() != Role.TEACHER) throw new CustomException(ErrorCode.NOT_TEACHER);
        //dto에 시작시간과 종료시간 없는 경우(null값도 가능하기 때문에 에러처리 해줘야함)
        if (addDto.getStartTime() == null || addDto.getEndTime() == null) throw new CustomException(ErrorCode.CANNOT_ADD_SCHEDULE);

        Connect connect = connectRepository.findById(addDto.getConnectId())
                .orElseThrow(() -> new CustomException(ErrorCode.NO_CONNECT_ID));
        Schedule schedule = Schedule.builder()
                .connect(connect)
                .isDeleted(false)   //추가
                .editDate(addDto.getAddDate())
                .startTime(addDto.getStartTime())
                .endTime(addDto.getEndTime())
                .build();
    }

    @Transactional
    public void deleteSchedule(DeleteScheduleDto deleteDto, Member loginMember) {
        //선생님만 일정 삭제 가능
        if (loginMember.getRole() != Role.TEACHER) throw new CustomException(ErrorCode.NOT_TEACHER);

        Connect connect = connectRepository.findById(deleteDto.getConnectId())
                .orElseThrow(() -> new CustomException(ErrorCode.NO_CONNECT_ID));
        Schedule schedule = Schedule.builder()
                .connect(connect)
                .isDeleted(true)   //삭제
                .editDate(deleteDto.getDeleteDate())
                .startTime(null)
                .endTime(null)
                .build();
    }
}
