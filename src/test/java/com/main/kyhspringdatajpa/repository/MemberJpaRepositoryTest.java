package com.main.kyhspringdatajpa.repository;

import com.main.kyhspringdatajpa.entity.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    void save() {
        Member member = new Member("memberA");
        Member savedmember = memberJpaRepository.save(member);
        Member foundMember = memberJpaRepository.find(savedmember.getId());

        assertEquals(foundMember.getId(), member.getId());
        assertEquals(foundMember.getUsername(), member.getUsername());
    }

    @Test
    void find() {
    }
}