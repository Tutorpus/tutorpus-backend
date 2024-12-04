package com.tutorpus.tutorpus.connect.repository;

import com.tutorpus.tutorpus.connect.entity.ClassDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClassDayRepository extends JpaRepository<ClassDay, Long> {

    //시작날짜 이후의 classday
    @Query(value = "SELECT * FROM class_day c " +
                    "WHERE c.connect_id = :connectId " +
                    "AND YEAR(c.start_date) <= :year AND MONTH(c.start_date) <= :month",
            nativeQuery = true)
    List<ClassDay> findByYearAndMonth(@Param("connectId") Long connectId, @Param("year") int year, @Param("month") int month);

}
