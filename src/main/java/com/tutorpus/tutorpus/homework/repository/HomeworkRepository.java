package com.tutorpus.tutorpus.homework.repository;

import com.tutorpus.tutorpus.homework.entity.Homework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface HomeworkRepository extends JpaRepository<Homework, Long> {
    Optional<Homework> findById(Long id);

    @Query(value = "SELECT * FROM homework h " +
            "WHERE h.connect_id = :connectId " +
            "AND h.class_date BETWEEN :startOfDay AND :endOfDay",
            nativeQuery = true)
    List<Homework> findByConnectIdAndDate(@Param("connectId") Long connectId,
                                          @Param("startOfDay") LocalDateTime startOfDay,
                                          @Param("endOfDay") LocalDateTime endOfDay);
}
