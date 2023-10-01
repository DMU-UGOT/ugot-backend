package com.dmuIt.domain.mapper;

import com.dmuIt.domain.dto.MemberDto;
import com.dmuIt.domain.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {
    Member memberPostDtoToMember(MemberDto.Post memberPostDto);

    default Member memberPatchDtoToMember(MemberDto.Patch memberPatchDto, Member findMember) {
        if (memberPatchDto == null) {
            return null;
        } else {
            Member member = new Member();
            member.setMemberId(findMember.getMemberId());
            member.setName(memberPatchDto.getName());
            member.setNickname(memberPatchDto.getNickname());
            member.setEmail(findMember.getEmail());
            member.setPassword(findMember.getPassword());
            member.setMajor(memberPatchDto.getMajor());
            member.setGrade(memberPatchDto.getGrade());
            member.set_class(memberPatchDto.get_class());
            member.setSkill(memberPatchDto.getSkill());
            member.setRoles(findMember.getRoles());
            member.setGitHubLink(memberPatchDto.getGitHubLink());
            member.setPersonalBlogLink(memberPatchDto.getPersonalBlogLink());
            member.setCreatedAt(findMember.getCreatedAt());
            member.setModifiedAt(findMember.getModifiedAt());
            return member;
        }
    }
    MemberDto.Response memberToMemberResponseDto(Member member);
}
