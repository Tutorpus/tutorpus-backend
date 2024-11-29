package com.tutorpus.tutorpus.connect.repository;

import com.tutorpus.tutorpus.connect.entity.ClassDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ClassDayRepository extends JpaRepository<ClassDay, Long> {

    //시작날짜 이후의 classday
    @Query("SELECT c FROM ClassDay c " +
            "WHERE YEAR(c.startDate) <= :year " +
            "AND MONTH(c.startDate) <= :month")
    List<ClassDay> findByYearAndMonth(@Param("year") int year, @Param("month") int month);
}
