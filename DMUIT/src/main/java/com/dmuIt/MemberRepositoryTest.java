package com.dmuIt;

import com.dmuIt.Repository.MemberRepository;

import com.dmuIt.domain.member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberRepositoryTest extends DmuitApplication {
    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/")
    public String getMain(){
        return "Main";
    }

    @GetMapping("/add")
    public String addMember(@RequestParam("id") Long id, @RequestParam("name") String name){
        memberRepository.save(new Member(id, name));
        return "add done";
    }
}
