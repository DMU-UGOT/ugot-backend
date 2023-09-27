package com.dmuIt.domain.mapper;

import com.dmuIt.domain.dto.MemberDto;
import com.dmuIt.domain.entity.Member;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-26T21:00:14+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 17.0.6 (Amazon.com Inc.)"
)
@Component
public class MemberMapperImpl implements MemberMapper {

    @Override
    public Member memberPostDtoToMember(MemberDto.Post memberPostDto) {
        if ( memberPostDto == null ) {
            return null;
        }

        Member member = new Member();

        member.setName( memberPostDto.getName() );
        member.setNickname( memberPostDto.getNickname() );
        member.setEmail( memberPostDto.getEmail() );
        member.setPassword( memberPostDto.getPassword() );
        member.setMajor( memberPostDto.getMajor() );
        member.setGrade( memberPostDto.getGrade() );
        member.set_class( memberPostDto.get_class() );
        member.setGitHubLink( memberPostDto.getGitHubLink() );
        member.setPersonalBlogLink( memberPostDto.getPersonalBlogLink() );
        List<String> list = memberPostDto.getSkill();
        if ( list != null ) {
            member.setSkill( new ArrayList<String>( list ) );
        }

        return member;
    }

    @Override
    public MemberDto.Response memberToMemberResponseDto(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberDto.Response.ResponseBuilder response = MemberDto.Response.builder();

        response.memberId( member.getMemberId() );
        response.name( member.getName() );
        response.nickname( member.getNickname() );
        response.email( member.getEmail() );
        response.major( member.getMajor() );
        response.grade( member.getGrade() );
        response._class( member.get_class() );
        List<String> list = member.getSkill();
        if ( list != null ) {
            response.skill( new ArrayList<String>( list ) );
        }
        response.gitHubLink( member.getGitHubLink() );
        response.personalBlogLink( member.getPersonalBlogLink() );
        response.createdAt( member.getCreatedAt() );
        response.modifiedAt( member.getModifiedAt() );

        return response.build();
    }
}
