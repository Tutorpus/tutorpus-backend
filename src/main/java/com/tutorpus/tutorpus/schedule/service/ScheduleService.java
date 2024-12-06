package com.tutorpus.tutorpus.schedule.service;

import com.tutorpus.tutorpus.connect.entity.ClassDay;
import com.tutorpus.tutorpus.connect.entity.Connect;
import com.tutorpus.tutorpus.connect.repository.ClassDayRepository;
import com.tutorpus.tutorpus.connect.repository.ConnectRepository;
import com.tutorpus.tutorpus.exception.CustomException;
import com.tutorpus.tutorpus.exception.ErrorCode;
import com.tutorpus.tutorpus.member.entity.Member;
import com.tutorpus.tutorpus.member.entity.Role;
import com.tutorpus.tutorpus.schedule.dto.AddScheduleDto;
import com.tutorpus.tutorpus.schedule.dto.DeleteScheduleDto;
import com.tutorpus.tutorpus.schedule.dto.EditScheduleDto;
import com.tutorpus.tutorpus.schedule.entity.Schedule;
import com.tutorpus.tutorpus.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ConnectRepository connectRepository;
    private final ClassDayRepository classDayRepository;

    @Transactional
    public void addSchedule(AddScheduleDto addDto, Member loginMember) {
        //선생님만 일정 추가 가능
        if (loginMember.getRole() != Role.TEACHER) throw new CustomException(ErrorCode.NOT_TEACHER);
        //dto에 시작시간과 종료시간 없는 경우(null값도 가능하기 때문에 에러처리 해줘야함)
        if (addDto.getStartTime() == null || addDto.getEndTime() == null)
            throw new CustomException(ErrorCode.CANNOT_ADD_SCHEDULE);

        Connect connect = connectRepository.findById(addDto.getConnectId())
                .orElseThrow(() -> new CustomException(ErrorCode.NO_CONNECT_ID));
        Schedule schedule = Schedule.builder()
                .connect(connect)
                .isDeleted(false)   //추가
                .editDate(addDto.getAddDate())
                .startTime(addDto.getStartTime())
                .endTime(addDto.getEndTime())
                .build();
        scheduleRepository.save(schedule);
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
        scheduleRepository.save(schedule);
    }

    @Transactional
    public void editSchedule(EditScheduleDto editDto, Member loginMember) {
        if (loginMember.getRole() != Role.TEACHER) throw new CustomException(ErrorCode.NOT_TEACHER);
        if (editDto.getStartTime() == null || editDto.getEndTime() == null)
            throw new CustomException(ErrorCode.CANNOT_ADD_SCHEDULE);

        Connect connect = connectRepository.findById(editDto.getConnectId())
                .orElseThrow(() -> new CustomException(ErrorCode.NO_CONNECT_ID));

        //삭제
        Schedule deleteSchedule = Schedule.builder()
                .connect(connect)
                .isDeleted(true)   //삭제
                .editDate(editDto.getEditDate())
                .startTime(null)
                .endTime(null)
                .build();
        scheduleRepository.save(deleteSchedule);

        //추가
        Schedule addSchedule = Schedule.builder()
                .connect(connect)
                .isDeleted(false)   //추가
                .editDate(editDto.getAddDate())
                .startTime(editDto.getStartTime())
                .endTime(editDto.getEndTime())
                .build();
        scheduleRepository.save(addSchedule);
    }

    //단순 저장한 classDay의 요일의 전체 날짜만 조회
    public List<LocalDate> onlyWithDaySchedule(int year, int month, Member member){
        //로그인된 member가 진행하고 있는 모든 과외(Connect) 들고오기
        List<Connect> memberConnects = connectRepository.findByMemberId(member.getId());
        //시작날짜가 지난 classDay list
        List<ClassDay> classDays = new ArrayList<>();
        for(Connect connect : memberConnects){
            classDays.addAll(classDayRepository.findByYearAndMonth(connect.getId(), year, month));
        }

        // 해당 월의 첫날과 마지막 날 계산 (YearMonth 클래스 사용)
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startOfMonth = yearMonth.atDay(1);
        LocalDate endOfMonth = yearMonth.atEndOfMonth();

        List<LocalDate> returnDates = new ArrayList<>();
        for(ClassDay c : classDays){
            List<LocalDate> matchingDates = startOfMonth.datesUntil(endOfMonth.plusDays(1))
                    .filter(date ->
                            !date.isBefore(c.getStartDate()) && // startDate 이후의 날짜
                                    date.getDayOfWeek() == c.getDay().getDayOfWeek()) // enum의 요일과 일치
                    .collect(Collectors.toList());
            returnDates.addAll(matchingDates);
        }
        return returnDates;
    }

    @Transactional(readOnly = true)
    //정말 전체 스케쥴
    public List<LocalDate> realScheduleList(int year, int month, Member member){
        List<Connect> connectList = connectRepository.findByMemberId(member.getId());
        List<LocalDate> onlyDaySchedule = onlyWithDaySchedule(year, month, member); //classDay의 요일의 전체 날짜만 조회
        //삭제 및 추가 리스트
        List<LocalDate> deleteList = new ArrayList<>();
        List<LocalDate> addList = new ArrayList<>();
        for(Connect connect : connectList){
            List<LocalDate> delete = scheduleRepository.findByConnectIdAndIsDeleted(connect.getId(), true).stream()
                    .map(schedule -> schedule.getEditDate())
                    .collect(Collectors.toList());
            deleteList.addAll(delete);
            List<LocalDate> add = scheduleRepository.findByConnectIdAndIsDeleted(connect.getId(), false).stream()
                    .map(schedule -> schedule.getEditDate())
                    .collect(Collectors.toList());
            addList.addAll(add);
        }
        //삭제
        for(LocalDate date : deleteList){
            if (onlyDaySchedule.contains(date)){
                onlyDaySchedule.remove(date);
            }
        }
        //추가
        for(LocalDate date : addList){
            if (!onlyDaySchedule.contains(date)){
                onlyDaySchedule.add(date);
            }
        }

        onlyDaySchedule.sort(Comparator.naturalOrder());
        return onlyDaySchedule;
    }
}
