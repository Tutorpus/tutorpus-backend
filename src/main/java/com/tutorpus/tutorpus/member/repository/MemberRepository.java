package com.tutorpus.tutorpus.member.repository;

import com.tutorpus.tutorpus.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository <Member, Long>{
}
