package com.tutorpus.tutorpus.schedule.repository;

import com.tutorpus.tutorpus.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    //삭제/추가 리스트만 들고오기
    @Query(value = "SELECT * FROM schedule s " +
                    "WHERE s.connect_id = :connectId AND s.is_deleted = :isDeleted",
            nativeQuery = true)
    List<Schedule> findByConnectIdAndIsDeleted(@Param("connectId") Long connectId, @Param("isDeleted") Boolean isDeleted);
}
