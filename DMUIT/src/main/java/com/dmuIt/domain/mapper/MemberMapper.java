package com.dmuIt.domain.mapper;

import com.dmuIt.domain.dto.MemberDto;
import com.dmuIt.domain.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {
    Member memberPostDtoToMember(MemberDto.Post memberPostDto);

    default Member memberPatchDtoToMember(MemberDto.Patch memberPatchDto, Member findMember) {
        if (memberPatchDto == null) {
            return null;
        } else {
            Member member = new Member();
            member.setMemberId(findMember.getMemberId());
            member.setName(findMember.getName());
            member.setNickname(memberPatchDto.getNickname());
            member.setEmail(findMember.getEmail());
            member.setPassword(findMember.getPassword());
            member.setPhone(findMember.getPhone());
            member.setMajor(memberPatchDto.getMajor());
            member.setGrade(findMember.getGrade());
            member.set_class(findMember.get_class());
            member.setRoles(findMember.getRoles());
            member.setCreatedAt(findMember.getCreatedAt());
            member.setModifiedAt(findMember.getModifiedAt());
            return member;
        }
    }
    MemberDto.Response memberToMemberResponseDto(Member member);
}
