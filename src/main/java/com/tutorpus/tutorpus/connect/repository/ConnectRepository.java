package com.tutorpus.tutorpus.connect.repository;

import com.tutorpus.tutorpus.connect.entity.Connect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConnectRepository extends JpaRepository<Connect, Long> {
    Optional<Connect> findById(Long id);

    //member의 모든 connect 들고오기
    @Query(value = "SELECT * FROM connect c WHERE c.teacher_id = :id OR c.student_id = :id",
            nativeQuery = true)
    List<Connect> findByMemberId(@Param("id") Long id);

    //이미 존재하는 connect인지 확인
    @Query(value = "SELECT * FROM connect c WHERE c.teacher_id = :teacher_id AND c.student_id = :student_id",
            nativeQuery = true)
    Connect findIfDuplicate(@Param("teacher_id") Long teacher_id, @Param("student_id") Long student_id);
}
