package com.main.kyhspringdatajpa.repository;

import com.main.kyhspringdatajpa.entity.Member;

import java.util.List;

public interface MemberRepositoryCustom {
    List<Member> findMemberCustom();
}
