package com.main.kyhspringdatajpa.repository;

import com.main.kyhspringdatajpa.entity.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    @Test
    public void basicCRUD() {

        //given
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        Member foundMember1 = memberRepository.findById(member1.getId()).get();
        Member foundMember2 = memberRepository.findById(member2.getId()).get();

        //then
        assertEquals(foundMember1.getId(), member1.getId());
        assertEquals(foundMember2.getId(), member2.getId());

        List<Member> all = memberRepository.findAll();
        assertEquals(2, all.size());

        long count = memberRepository.count();
        assertEquals(2, count);

        memberRepository.delete(member1);
        memberRepository.delete(member2);

        long deletedCount = memberRepository.count();
        assertEquals(0, deletedCount);
    }

    @Test
    public void findByUserNameAndAgeGreaterThan() {
        //given

        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);

        //when
        memberRepository.save(m1);
        memberRepository.save(m2);

        //then
        List<Member> members = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 10);
        assertEquals(1, members.size());
    }


}