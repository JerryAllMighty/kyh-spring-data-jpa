package com.main.kyhspringdatajpa.repository;

import com.main.kyhspringdatajpa.entity.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void testMember() {
        Member member = new Member("memberB");
        Member savedMember = memberRepository.save(member);
        Member foundMember = memberRepository.findById(savedMember.getId()).get();

        Assertions.assertEquals(foundMember.getId(), member.getId());
        Assertions.assertEquals(foundMember.getUsername(), member.getUsername());
        Assertions.assertEquals(foundMember, member);
    }


}