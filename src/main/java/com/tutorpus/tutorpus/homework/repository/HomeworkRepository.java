package com.tutorpus.tutorpus.homework.repository;

import com.tutorpus.tutorpus.homework.entity.Homework;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeworkRepository extends JpaRepository<Homework, Long> {
}
