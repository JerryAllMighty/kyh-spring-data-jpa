package com.main.kyhspringdatajpa.repository;

import com.main.kyhspringdatajpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
