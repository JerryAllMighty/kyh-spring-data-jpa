package com.main.kyhspringdatajpa.repository;

import com.main.kyhspringdatajpa.dto.MemberDto;
import com.main.kyhspringdatajpa.entity.Member;
import com.main.kyhspringdatajpa.entity.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

    @PersistenceContext
    EntityManager em;

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

    @Test
    public void test() {
        //given
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);

        //when
        memberRepository.save(m1);
        memberRepository.save(m2);
        List<String> members = memberRepository.findUsernameList();

        //when

        //then
        members.forEach(x -> System.out.println(x));
    }

    @Test
    public void testDto() {
        //given
        Team t1 = new Team("teamA");
        teamRepository.save(t1);

        //when
        Member m1 = new Member("AAA", 10, t1);
        memberRepository.save(m1);

        //then
        List<MemberDto> members = memberRepository.findMemberDto();
        for (MemberDto member : members) {
            System.out.println(member);

        }

    }

    @Test
    void findByNames() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByNames(Arrays.asList("AAA", "BBB"));
        for (Member member : result) {
            System.out.println(member);

        }

    }

    @Test
    public void returnType() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);

        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findListByUsername("AAA");
        for (Member member : result) {
            System.out.println(member);

        }
    }

    @Test
    public void paging() {
        //given
        memberRepository.save(new Member("AA1", 10));
        memberRepository.save(new Member("AA2", 10));
        memberRepository.save(new Member("AA3", 10));
        memberRepository.save(new Member("AA4", 10));
        memberRepository.save(new Member("AA5", 10));

        int age = 10;
//        int offset = 0;
//        int limit = 3;

        PageRequest pageRequest = PageRequest.of(1, 2, Sort.by(Sort.Direction.DESC, "username"));

        //when
        Page<Member> page = memberRepository.findByAge(age, pageRequest);
        List<Member> content = page.getContent();
        long totalElements = page.getTotalElements();

        for (Member member : page) {
            System.out.println(member);
        }
        System.out.println(totalElements);

        assertEquals(2, content.size());
        assertEquals(5, page.getTotalElements());
        assertEquals(1, page.getNumber());
        assertEquals(3, page.getTotalPages());
        assertEquals(false, page.isFirst());

        Slice<Member> slice = memberRepository.findSliceByAge(age, pageRequest);
        List<Member> content2 = slice.getContent();
        assertEquals(2, content2.size());
//        assertEquals(5, slice.getTotalElements());
        assertEquals(1, slice.getNumber());
//        assertEquals(3, slice.getTotalPages());
        assertEquals(false, slice.isFirst());
        assertEquals(true, slice.hasNext());

    }

    @Test
    void bulkAgePlus() {
        //given
        memberRepository.save(new Member("AA1", 10));
        memberRepository.save(new Member("AA2", 19));
        memberRepository.save(new Member("AA3", 20));
        memberRepository.save(new Member("AA4", 21));
        memberRepository.save(new Member("AA5", 40));

        int resultCount = memberRepository.bulkAgePlus(20);
//        em.flush();
        em.clear();

        Member result = memberRepository.findListByUsername("AA5").get(0);

        System.out.println(result);
//        System.out.println();

    }
}
