package com.tutorpus.tutorpus.feedback.repository;

import com.tutorpus.tutorpus.feedback.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
