package com.tutorpus.tutorpus.connect.entity;

import com.tutorpus.tutorpus.exception.CustomException;
import com.tutorpus.tutorpus.exception.ErrorCode;

import java.time.DayOfWeek;

public enum Day {
    MON, TUE, WED, THUR, FRI, SAT, SUN;

    public DayOfWeek getDayOfWeek() {
        switch (this) {
            case MON:
                return DayOfWeek.MONDAY;
            case TUE:
                return DayOfWeek.TUESDAY;
            case WED:
                return DayOfWeek.WEDNESDAY;
            case THUR:
                return DayOfWeek.THURSDAY;
            case FRI:
                return DayOfWeek.FRIDAY;
            case SAT:
                return DayOfWeek.SATURDAY;
            case SUN:
                return DayOfWeek.SUNDAY;
            default:
                throw new CustomException(ErrorCode.NO_EXIST_DAY);
        }
    }
}
