package com.main.kyhspringdatajpa.repository;

import com.main.kyhspringdatajpa.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
