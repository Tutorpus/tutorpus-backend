package com.tutorpus.tutorpus.student.repository;

import com.tutorpus.tutorpus.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
