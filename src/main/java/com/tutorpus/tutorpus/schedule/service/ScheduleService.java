package com.tutorpus.tutorpus.schedule.service;

import com.tutorpus.tutorpus.connect.entity.ClassDay;
import com.tutorpus.tutorpus.connect.entity.Connect;
import com.tutorpus.tutorpus.connect.repository.ClassDayRepository;
import com.tutorpus.tutorpus.connect.repository.ConnectRepository;
import com.tutorpus.tutorpus.exception.CustomException;
import com.tutorpus.tutorpus.exception.ErrorCode;
import com.tutorpus.tutorpus.member.entity.Member;
import com.tutorpus.tutorpus.member.entity.Role;
import com.tutorpus.tutorpus.schedule.dto.*;
import com.tutorpus.tutorpus.schedule.entity.Schedule;
import com.tutorpus.tutorpus.schedule.repository.ScheduleRepository;
import com.tutorpus.tutorpus.student.entity.Student;
import com.tutorpus.tutorpus.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ConnectRepository connectRepository;
    private final ClassDayRepository classDayRepository;
    private final StudentRepository studentRepository;

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

        //삭제하려는 날짜가 수정된 날짜인 경우 - 수정된 날짜 자체를 테이블에서 제거
        Schedule existSchedule = scheduleRepository.findIfIsAlreadyEdited(connect.getId(), deleteDto.getDeleteDate().toLocalDate(), deleteDto.getDeleteDate().toLocalTime(), false);
        if(existSchedule != null){
            scheduleRepository.delete(existSchedule);
        }
        else{
            Schedule schedule = Schedule.builder()
                    .connect(connect)
                    .isDeleted(true)   //삭제
                    .editDate(deleteDto.getDeleteDate().toLocalDate())
                    .startTime(deleteDto.getDeleteDate().toLocalTime())
                    .endTime(null)
                    .build();
            scheduleRepository.save(schedule);
        }
    }

    @Transactional
    public void editSchedule(EditScheduleDto editDto, Member loginMember) {
        if (loginMember.getRole() != Role.TEACHER) throw new CustomException(ErrorCode.NOT_TEACHER);
        if (editDto.getStartTime() == null || editDto.getEndTime() == null)
            throw new CustomException(ErrorCode.CANNOT_ADD_SCHEDULE);

        Connect connect = connectRepository.findById(editDto.getConnectId())
                .orElseThrow(() -> new CustomException(ErrorCode.NO_CONNECT_ID));

        //삭제하려는 날짜가 수정된 날짜인 경우 - 이미 수정된 날짜 자체를 재수정
        Schedule existSchedule = scheduleRepository.findIfIsAlreadyEdited(connect.getId(), editDto.getEditDate().toLocalDate(), editDto.getEditDate().toLocalTime(), false);
        if(existSchedule != null){
            existSchedule.updateSchedule(connect, editDto.getAddDate(), editDto.getStartTime(), editDto.getEndTime());
        }
        else{
            //삭제
            Schedule deleteSchedule = Schedule.builder()
                    .connect(connect)
                    .isDeleted(true)   //삭제
                    .editDate(editDto.getEditDate().toLocalDate())
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
    }

    //단순 저장한 classDay의 요일의 전체 날짜만 조회
    public List<LocalDate> onlyWithDayScheduleAndConnect(int year, int month, Connect connect){
        //시작날짜가 지난 classDay list
        List<ClassDay> classDays = classDayRepository.findByYearAndMonth(connect.getId(), year, month);

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
    public Map<Long, ClassReturnDto> realScheduleList(int year, int month, Member member) {
        List<Connect> connectList = connectRepository.findByMemberId(member.getId());
        Map<Long, ClassReturnDto> scheduleMap = new HashMap<>();   // 결과저장용 Map

        for (Connect connect : connectList) {
            // 각 Connect에 대한 날짜 리스트를 구함
            Student student = studentRepository.findByMemberId(connect.getStudent().getId());
            List<LocalDate> onlyDaySchedule = onlyWithDayScheduleAndConnect(year, month, connect); // Connect별로 처리
            List<LocalDate> deleteList = scheduleRepository.findByConnectIdAndIsDeleted(connect.getId(), true).stream()
                    .map(schedule -> schedule.getEditDate())
                    .collect(Collectors.toList());
            List<LocalDate> addList = scheduleRepository.findByConnectIdAndIsDeleted(connect.getId(), false).stream()
                    .map(schedule -> schedule.getEditDate())
                    .collect(Collectors.toList());

            // 삭제
            for (LocalDate date : deleteList) {
                onlyDaySchedule.remove(date);
            }
            // 추가
            for (LocalDate date : addList) {
                if (!onlyDaySchedule.contains(date)) {
                    onlyDaySchedule.add(date);
                }
            }

            // 날짜 정렬
            onlyDaySchedule.sort(Comparator.naturalOrder());
            ClassReturnDto dayDto = new ClassReturnDto(student.getColor(), onlyDaySchedule);
            scheduleMap.put(connect.getId(), dayDto);
        }

        return scheduleMap;
    }

    @Transactional(readOnly = true)
    //하루 상세 스케쥴
    public List<ClassDto> scheduleDetail(int year, int month, int day, Member member) {
        List<ClassDto> classDtos = new ArrayList<>();
        List<Connect> connectList = connectRepository.findByMemberId(member.getId());
        LocalDate clickDate = LocalDate.of(year, month, day);   //찾으려는 날짜
        //시작날짜가 지난 classDay list
        for(Connect connect : connectList){
            List<ClassDay> classDays = classDayRepository.findByYearAndMonth(connect.getId(), year, month);

            for (ClassDay classDay : classDays) {
                // 클릭한 날짜의 요일과 classDay의 요일 비교
                if (classDay.getDay().getDayOfWeek() == clickDate.getDayOfWeek()) {
                    //학생정보
                    Student student = studentRepository.findByMemberId(connect.getStudent().getId());
                    ClassDto dto = ClassDto.builder()
                            .connectId(connect.getId())
                            .studentName(connect.getStudent().getName())
                            .subject(connect.getSubject())
                            .color(student.getColor())
                            .startTime(classDay.getStartTime())
                            .endTime(classDay.getEndTime())
                            .build();
                    classDtos.add(dto);
                }
            }
        }

        //삭제 - 해당 날짜가 삭제 리스트에 있는 경우
        for (int i = classDtos.size() - 1; i >= 0; i--) {
            ClassDto regularClass = classDtos.get(i);
            if (!scheduleRepository.findByConnectIdAndDateAndIsDeleted(regularClass.getConnectId(), clickDate, true).isEmpty()) {
                classDtos.remove(i);
            }
        }

        //추가 - 해당 날짜가 추가 리스트에 있는 경우
        for(Connect connect : connectList) {
            List<Schedule> addDate = scheduleRepository.findByConnectIdAndDateAndIsDeleted(connect.getId(), clickDate, false);
            //학생정보
            Student student = studentRepository.findByMemberId(connect.getStudent().getId());
            for(Schedule add : addDate){
                ClassDto dto = ClassDto.builder()
                        .connectId(connect.getId())
                        .studentName(connect.getStudent().getName())
                        .subject(connect.getSubject())
                        .color(student.getColor())
                        .startTime(add.getStartTime())
                        .endTime(add.getEndTime())
                        .build();
                classDtos.add(dto);
            }
        }
        return classDtos;
    }

    @Transactional(readOnly = true)
    public Map<LocalDate, StudentScheduleDto> scheduleStudentDetail(int year, int month, Member loginMember, Long connectId) {
        Connect connect = connectRepository.findById(connectId)
                        .orElseThrow(()-> new CustomException(ErrorCode.NO_CONNECT_ID));
        Map<LocalDate, StudentScheduleDto> returnDto = new HashMap<>(); //반환 dto

        //추가/삭제 제외 날짜 리스트
        List<ClassDay> classDays = classDayRepository.findByYearAndMonth(connect.getId(), year, month);
        // 해당 월의 첫날과 마지막 날 계산 (YearMonth 클래스 사용)
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startOfMonth = yearMonth.atDay(1);
        LocalDate endOfMonth = yearMonth.atEndOfMonth();

        for(ClassDay c : classDays){
            Map<LocalDate, StudentScheduleDto> matchingDates = startOfMonth.datesUntil(endOfMonth.plusDays(1))
                    .filter(date ->
                            !date.isBefore(c.getStartDate()) && // startDate 이후의 날짜
                                    date.getDayOfWeek() == c.getDay().getDayOfWeek()) // enum의 요일과 일치
                    .collect(Collectors.toMap(
                            date -> date,
                            date -> {
                                StudentScheduleDto dto = StudentScheduleDto.builder()
                                        .day(c.getDay().getDayOfWeek())
                                        .startTime(c.getStartTime())
                                        .endTime(c.getEndTime())
                                        .build();
                                return dto;
                            }
                    ));
            returnDto.putAll(matchingDates);
        }

        //삭제 후 추가
        List<Schedule> deleteSchedule = scheduleRepository.findByConnectIdAndIsDeleted(connect.getId(), true);
        List<Schedule> addSchedule = scheduleRepository.findByConnectIdAndIsDeleted(connect.getId(), false);
        // 삭제
        for (Schedule s : deleteSchedule) {
            returnDto.remove(s.getEditDate());
        }
        // 추가
        for (Schedule s : addSchedule) {
            if (!returnDto.containsKey(s.getEditDate())) {
                StudentScheduleDto dto = StudentScheduleDto.builder()
                        .day(s.getEditDate().getDayOfWeek())
                        .startTime(s.getStartTime())
                        .endTime(s.getEndTime())
                        .build();
                returnDto.put(s.getEditDate(), dto);
            }
        }

        //정렬 후 반환
        Map<LocalDate, StudentScheduleDto> sortedMap = new TreeMap<>(returnDto);   
        return sortedMap;
    }
}
