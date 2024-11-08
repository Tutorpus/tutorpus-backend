package com.tutorpus.tutorpus.member.repository;

import com.tutorpus.tutorpus.member.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
