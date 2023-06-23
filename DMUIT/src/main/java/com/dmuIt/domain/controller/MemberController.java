package com.dmuIt.domain.controller;

import com.dmuIt.domain.entity.Member;
import com.dmuIt.domain.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    private Member signUp(@RequestBody Member member) {
        memberService.createMember(member);
        return member;
    }
}
