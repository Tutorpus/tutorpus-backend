package com.tutorpus.tutorpus.schedule.repository;

import com.tutorpus.tutorpus.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    //삭제/추가 리스트 조회
    @Query(value = "SELECT * FROM schedule s " +
                    "WHERE s.connect_id = :connectId AND s.is_deleted = :isDeleted",
            nativeQuery = true)
    List<Schedule> findByConnectIdAndIsDeleted(@Param("connectId") Long connectId,
                                               @Param("isDeleted") Boolean isDeleted);

    //해당 날짜의 삭제/추가 조회
    @Query(value = "SELECT * FROM schedule s " +
            "WHERE s.connect_id = :connectId AND s.is_deleted = :isDeleted AND s.edit_date = :date",
            nativeQuery = true)
    List<Schedule> findByConnectIdAndDateAndIsDeleted(@Param("connectId") Long connectId,
                                                      @Param("date") LocalDate date,
                                                      @Param("isDeleted") Boolean isDeleted);

    //이미 수정된 수업인지 조회
    @Query(value = "SELECT * FROM schedule s " +
            "WHERE s.connect_id = :connectId AND s.is_deleted = :isDeleted AND s.edit_date = :date AND s.start_time = :time",
            nativeQuery = true)
    Schedule findIfIsAlreadyEdited(@Param("connectId") Long connectId,
                                         @Param("date") LocalDate date,
                                         @Param("time") LocalTime time,
                                         @Param("isDeleted") Boolean isDeleted);
}
