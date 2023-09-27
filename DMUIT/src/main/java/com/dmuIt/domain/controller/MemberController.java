package com.dmuIt.domain.controller;

import com.dmuIt.domain.dto.ApiResponseDto;
import com.dmuIt.domain.dto.MemberDto;
import com.dmuIt.domain.entity.Member;
import com.dmuIt.domain.mapper.MemberMapper;
import com.dmuIt.domain.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper memberMapper;
    private final ApiResponseDto apiResponseDto;

    @PostMapping
    private MemberDto.Response signUp(@RequestBody MemberDto.Post memberPostDto) {
        Member member = memberMapper.memberPostDtoToMember(memberPostDto);
        return memberService.createMember(member);
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(HttpServletRequest request,
                                 @RequestBody @Valid MemberDto.SignIn signIn, Errors errors) {
        if (errors.hasErrors()) {
            return apiResponseDto.fail(errors);
        }
        return memberService.signIn(request, signIn);
    }

    @GetMapping("/check/{nickname}")
    public String checkNickname(@PathVariable("nickname") String nickname) {
        return memberService.checkNickname(nickname);
    }

    @PostMapping("/reissue")
    public ResponseEntity reissue(HttpServletRequest request) {
        return memberService.reissue(request);
    }

    @PatchMapping("/{member-id}")
    public MemberDto.Response updateMember(@PathVariable("member-id") long memberId,
                                           HttpServletRequest request,
                                           @RequestBody MemberDto.Patch memberPatchDto) {
        Member findMember = memberService.findVerifiedMember(memberId);
        Member member = memberMapper.memberPatchDtoToMember(memberPatchDto, findMember);

        Member updatedMember = memberService.updateMember(request, member);

        return memberMapper.memberToMemberResponseDto(updatedMember);
    }

    @GetMapping("/{member-id}")
    public MemberDto.Response getMember(@PathVariable("member-id") long memberId) {
        Member verifiedMember = memberService.findVerifiedMember(memberId);
        return memberMapper.memberToMemberResponseDto(verifiedMember);
    }

    @DeleteMapping("/{member-id}")
    public void deleteMember(HttpServletRequest request, @PathVariable("member-id") long memberId) {
        memberService.deleteMember(request, memberId);
    }
}
