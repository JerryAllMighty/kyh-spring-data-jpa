package com.main.kyhspringdatajpa.entity;

import com.main.kyhspringdatajpa.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
//@Rollback(value = false)
class MemberTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void testEntity() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamB);
        Member member3 = new Member("member3", 30, teamA);
        Member member4 = new Member("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        em.flush();
        em.clear();

        //확인
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        for (Member member : members) {
            System.out.println("Member = " + member);
            System.out.println("Member.team = " + member.getTeam());
            System.out.println("Member.team = " + member.getTeam().getName());

        }
    }

    @Test
    public void BaseEntityTest() {
        //given
        Member member = new Member("memberA", 10);
        memberRepository.save(member);
        member.changeUsername("username B");
        em.flush();
        em.clear();


        //when
        Member foundMember = memberRepository.findById(member.getId()).get();
        System.out.println(foundMember.getCreatedDate());
        System.out.println(foundMember.getLastModifiedDate());

        System.out.println(foundMember.getCreatedBy());
        System.out.println(foundMember.getLastModifiedBy());
        //then
    }

}