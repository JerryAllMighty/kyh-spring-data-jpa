package com.main.kyhspringdatajpa.repository;

import com.main.kyhspringdatajpa.entity.Member;
import com.main.kyhspringdatajpa.entity.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Test
    public void basicCRUD() {

        //given
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        Member foundMember1 = memberJpaRepository.findById(member1.getId()).get();
        Member foundMember2 = memberJpaRepository.findById(member2.getId()).get();

        //then
        assertEquals(foundMember1.getId(), member1.getId());
        assertEquals(foundMember2.getId(), member2.getId());

        List<Member> all = memberJpaRepository.findALl();
        assertEquals(2, all.size());

        long count = memberJpaRepository.count();
        assertEquals(2, count);

        memberJpaRepository.delete(member1);
        memberJpaRepository.delete(member2);

        long deletedCount = memberJpaRepository.count();
        assertEquals(0, deletedCount);
    }

    @Test
    public void findByUserNameAndAgeGreaterThan() {
        //given

        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);

        //when
        memberJpaRepository.save(m1);
        memberJpaRepository.save(m2);

        //then
        List<Member> members = memberJpaRepository.findByUserNameAndAgeGreaterThan("AAA", 10);
        assertEquals(1, members.size());
    }

    @Test
    public void paging() {
        //given
        memberJpaRepository.save(new Member("AAA", 10));
        memberJpaRepository.save(new Member("AA2", 10));
        memberJpaRepository.save(new Member("AA3", 10));
        memberJpaRepository.save(new Member("AA4", 10));
        memberJpaRepository.save(new Member("AA5", 10));

        int age = 10;
        int offset = 0;
        int limit = 3;

        //when
        List<Member> members = memberJpaRepository.findByPage(age, offset, limit);
        long totalCount = memberJpaRepository.totalCount(age);

        //then
        assertEquals(members.size(), 3);
        assertEquals(totalCount, 5);
    }
}