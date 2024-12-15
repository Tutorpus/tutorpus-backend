package com.tutorpus.tutorpus.student.repository;

import com.tutorpus.tutorpus.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query(value = "SELECT * FROM student s WHERE s.member_id = :id",
            nativeQuery = true)
    Student findByMemberId(@Param("id") Long id);
}
