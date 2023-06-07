package com.dmuIt.Controller;

import com.dmuIt.domain.member.Member;
import com.dmuIt.service.MemberService;
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
