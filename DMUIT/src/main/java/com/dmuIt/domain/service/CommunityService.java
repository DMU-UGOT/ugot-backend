package com.dmuIt.domain.service;

import com.dmuIt.domain.dto.CommunityRequestDto;
import com.dmuIt.domain.dto.CommunityResponseDto;
import com.dmuIt.domain.entity.Comment;
import com.dmuIt.domain.entity.Community;
import com.dmuIt.domain.entity.Member;
import com.dmuIt.domain.repository.CommunityRepository;
import com.dmuIt.global.exception.BusinessLogicException;
import com.dmuIt.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final MemberService memberService;


    /**
     * 게시글 생성
     */
    @Transactional
    public void save(HttpServletRequest request, final CommunityRequestDto params) {
        Member member = memberService.verifiedCurrentMember(request);
        Community community = params.toEntity();
        community.setMember(member);
        communityRepository.save(community);
    }

    /**
     * 게시글 리스트 조회
     */
    public List<CommunityResponseDto> findAll() {
        List<Community> list = communityRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        return list.stream().map(CommunityResponseDto::new).collect(Collectors.toList());
    }

    /**
     * 게시글 상세조회
     */
    @Transactional
    public CommunityResponseDto findById(final Long id) {
        Community entity = communityRepository.findById(id).orElseThrow();
        entity.increaseViews();
        return new CommunityResponseDto(entity);
    }


    /**
     * 게시글 수정
     */
    @Transactional
    public void update(HttpServletRequest request, final Long id, final CommunityRequestDto params) {

        Community entity = communityRepository.findById(id).orElseThrow();
        Member member = memberService.verifiedCurrentMember(request);
        if (entity.getMember().getMemberId() != member.getMemberId()) {
            throw new BusinessLogicException(ExceptionCode.NO_PERMISSION);
        }
        //Community entity = communityRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        entity.update(params.toEntity().getTitle(), params.toEntity().getContent());
        entity.setModifiedAt(LocalDateTime.now());
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public void delete(HttpServletRequest request, final Long id) {
        Community entity = communityRepository.findById(id).orElseThrow();
        Member member = memberService.verifiedCurrentMember(request);
        if (entity.getMember().getMemberId() != member.getMemberId()) {
            throw new BusinessLogicException(ExceptionCode.NO_PERMISSION);
        }
        entity.delete();
    }

    public void verifiedCommunity(Long id) {
        Optional<Community> optionalCommunity = communityRepository.findById(id);
        optionalCommunity.orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMUNITY_NOT_FOUND));
    }
}

