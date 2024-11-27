package com.tutorpus.tutorpus.connect.repository;

import com.tutorpus.tutorpus.connect.entity.Connect;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConnectRepository extends JpaRepository<Connect, Long> {
    Optional<Connect> findById(Long id);
}
