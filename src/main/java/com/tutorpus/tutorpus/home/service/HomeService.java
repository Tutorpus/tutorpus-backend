package com.tutorpus.tutorpus.home.service;

import com.tutorpus.tutorpus.home.dto.HomeReturnDto;
import com.tutorpus.tutorpus.homework.dto.ReturnHomeworkDto;
import com.tutorpus.tutorpus.homework.service.HomeworkService;
import com.tutorpus.tutorpus.member.entity.Member;
import com.tutorpus.tutorpus.schedule.dto.ClassDto;
import com.tutorpus.tutorpus.schedule.dto.ClassReturnDto;
import com.tutorpus.tutorpus.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class HomeService {
    private final HomeworkService homeworkService;
    private final ScheduleService scheduleService;

    @Transactional(readOnly = true)
    public HomeReturnDto comingSoon(Member member) {
        LocalDateTime today = LocalDateTime.now();
        Map<Long, ClassReturnDto> allSchedule = scheduleService.realScheduleList(today.getYear(), today.getMonthValue(), member);
        //이번달에 다음 수업이 없는 경우 다음달로 넘어감.
        LocalDate nextMonth = LocalDate.now().plusMonths(1);
        Map<Long, ClassReturnDto> nextMonthSchedule = scheduleService.realScheduleList(nextMonth.getYear(), nextMonth.getMonthValue(), member);

        List<LocalDate> sortedDates = allSchedule.values().stream()
                .flatMap(classReturnDto -> classReturnDto.getDates().stream())
                .filter(date -> !date.isBefore(today.toLocalDate())) // 오늘 이후의 날짜들
                .distinct() // 중복 제거
                .sorted()
                .collect(Collectors.toList());
        // 다음 달 날짜 추가 (현재 달에 남은 날짜가 부족할 경우)
        if (sortedDates.size() < 2) {
            List<LocalDate> nextMonthDates = nextMonthSchedule.values().stream()
                    .flatMap(classReturnDto -> classReturnDto.getDates().stream())
                    .filter(date -> !date.isBefore(today.toLocalDate())) // 오늘 이후의 날짜들
                    .distinct() // 중복 제거
                    .sorted()
                    .collect(Collectors.toList());
            sortedDates.addAll(nextMonthDates);
        }

        LocalDate firstDate = sortedDates.size() > 0 ? sortedDates.get(0) : null;   //가장 최근의 날짜
        LocalDate secondDate = sortedDates.size() > 1 ? sortedDates.get(1) : null;  //그 다음의 날짜
        ClassDto classDto = null;
        LocalDate selectedDate = null;
        //가장 최근 날짜의 전체 수업
        if(firstDate != null){
            List<ClassDto> classes = scheduleService.scheduleDetail(firstDate.getYear(), firstDate.getMonthValue(), firstDate.getDayOfMonth(), member);
            for (ClassDto c : classes){
                LocalDateTime date = LocalDateTime.of(firstDate, c.getStartTime());
                if(date.isAfter(today)){
                    classDto = c;
                    selectedDate = firstDate;
                    break;
                }
            }
        }
        // firstDate에서 유효한 클래스가 없으면 secondDate 검사
        if (classDto == null && secondDate != null) {
            List<ClassDto> classes = scheduleService.scheduleDetail(secondDate.getYear(), secondDate.getMonthValue(), secondDate.getDayOfMonth(), member);
            for (ClassDto c : classes) {
                LocalDateTime dateTime = LocalDateTime.of(secondDate, c.getStartTime());
                if (dateTime.isAfter(LocalDateTime.now())) { // 현재 시간 이후의 수업
                    classDto = c;
                    selectedDate = secondDate;
                    break;
                }
            }
        }

        //숙제 확인
        List<ReturnHomeworkDto> homeworkDto = null;
        if(classDto != null){
            homeworkDto = homeworkService.getHomework(member, classDto.getConnectId(), selectedDate);
        }

        return new HomeReturnDto(member.getName(), selectedDate, classDto, homeworkDto);
    }
}
