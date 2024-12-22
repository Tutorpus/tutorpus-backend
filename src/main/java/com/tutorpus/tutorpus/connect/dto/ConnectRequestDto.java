package com.tutorpus.tutorpus.connect.dto;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class ConnectRequestDto {
    private String subject; //과목
    private String studentEmail;
    private String color;
    private List<TimeSlotDto> timeSlots; // 요일 및 시간 정보 리스트
}
