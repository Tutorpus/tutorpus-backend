package com.tutorpus.tutorpus.schedule.repository;

import com.tutorpus.tutorpus.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
