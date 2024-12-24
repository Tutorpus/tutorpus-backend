package com.tutorpus.tutorpus.connect.repository;

import com.tutorpus.tutorpus.connect.entity.ClassDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ClassDayRepository extends JpaRepository<ClassDay, Long> {

    //시작날짜 이후의 classday
    @Query(value = "SELECT * FROM class_day c " +
            "WHERE c.connect_id = :connectId " +
            "AND c.start_date <= :endOfMonth",
            nativeQuery = true)
    List<ClassDay> findByStartDateBefore(@Param("connectId") Long connectId,
                                         @Param("endOfMonth") LocalDate endOfMonth);
}
