package com.main.kyhspringdatajpa.controller;

import com.main.kyhspringdatajpa.dto.MemberDto;
import com.main.kyhspringdatajpa.entity.Member;
import com.main.kyhspringdatajpa.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    @GetMapping("/members2/{id}")
    public String findMember(@PathVariable("id") Member member) {
        //도메인 클래스 컨버터
        return member.getUsername();
    }

    @GetMapping("/members")
    public Page<MemberDto> list(@PageableDefault(size = 5) Pageable pageable) {
        Page<Member> page = memberRepository.findAll(pageable);
        Page<MemberDto> map = page.map(x -> new MemberDto(x.getId(), x.getUsername(), null));
        return map;
    }

    @PostConstruct
    public void init() {
        int nameIndex = 1;
        for (int i = 0; i < 100; i++) {
            memberRepository.save(new Member("member" + Integer.toString(nameIndex++), i));
        }
    }
}
