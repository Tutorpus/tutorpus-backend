package com.tutorpus.tutorpus.schedule.dto;

import com.tutorpus.tutorpus.connect.entity.ClassDay;
import com.tutorpus.tutorpus.connect.entity.Connect;
import com.tutorpus.tutorpus.member.entity.Member;
import com.tutorpus.tutorpus.student.entity.Student;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Getter
@NoArgsConstructor
public class ClassDto {
    Long connectId; //서버개발용. 프론트 필요x
    String studentName; //선생님 스케쥴용
    String subject; //학생 스케쥴용
    String color;
    LocalTime startTime;
    LocalTime endTime;

    public static ClassDto toDto(Connect connect, LocalTime startTime, LocalTime endTime) {
        ClassDto dto = new ClassDto();
        dto.connectId = connect.getId();
        dto.studentName = connect.getStudent().getName();
        dto.subject = connect.getSubject();
        dto.color = connect.getColor();
        dto.startTime = startTime;
        dto.endTime = endTime;
        return dto;
    }
}
