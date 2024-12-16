package com.tutorpus.tutorpus.feedback.repository;

import com.tutorpus.tutorpus.feedback.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    @Query(value = "SELECT * FROM feedback f " +
            "WHERE f.class_date = :classDate AND f.connect_id = :connectId",
            nativeQuery = true)
    Feedback findByConnectIdAndClassDate(@Param("connectId") Long connectId,
                                         @Param("classDate") LocalDateTime classDate);
}
