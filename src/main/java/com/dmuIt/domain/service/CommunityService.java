package com.dmuIt.domain.service;

import com.dmuIt.domain.dto.CommunityRequestDto;
import com.dmuIt.domain.dto.CommunityResponseDto;
import com.dmuIt.domain.entity.Comment;
import com.dmuIt.domain.entity.Community;
import com.dmuIt.domain.entity.Member;
import com.dmuIt.domain.entity.Team;
import com.dmuIt.domain.mapper.CommunityMapper;
import com.dmuIt.domain.repository.CommunityRepository;
import com.dmuIt.global.exception.BusinessLogicException;
import com.dmuIt.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final CommunityMapper communityMapper;
    private final MemberService memberService;

    @Transactional
    public void save(HttpServletRequest request, final CommunityRequestDto params) {
        Member member = memberService.verifiedCurrentMember(request);
        Community community = params.toEntity();
        community.setMember(member);
        communityRepository.save(community);
    }

//    @Transactional
//    public List<CommunityResponseDto> findAll() {
//        List<Community> list = communityRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
//        return list.stream().map(CommunityResponseDto::new).collect(Collectors.toList());
//    }


    @Transactional
    public CommunityResponseDto findById(final Long id) {
        Community entity = communityRepository.findById(id).orElseThrow();
        entity.increaseViews();
        return communityMapper.comToComResponseDto(entity);
    }

    @Transactional
    public Page<Community> findCommunities(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return communityRepository.findAllByOrderByCreatedAtDesc(pageRequest);
    }

    @Transactional
    public List<Community> findMyCommunities(HttpServletRequest request) {
        Member member = memberService.verifiedCurrentMember(request);
        return communityRepository.findCommunitiesByMember(member);
    }

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

    @Transactional
    public void delete(HttpServletRequest request, final Long id) {
        Community entity = communityRepository.findById(id).orElseThrow();
        Member member = memberService.verifiedCurrentMember(request);
        if (entity.getMember().getMemberId() != member.getMemberId()) {
            throw new BusinessLogicException(ExceptionCode.NO_PERMISSION);
        }
        communityRepository.delete(entity);
    }

    @Transactional
    public void refreshCommunity(HttpServletRequest request, long communityId) {
        Community community = findVerifiedCommunity(communityId);
        Member member = memberService.verifiedCurrentMember(request);
        if (community.getMember().getMemberId() != member.getMemberId()) {
            throw new BusinessLogicException(ExceptionCode.NO_PERMISSION);
        }
        community.setCreatedAt(LocalDateTime.now());
    }

    public void verifiedCommunity(Long id) {
        Optional<Community> optionalCommunity = communityRepository.findById(id);
        optionalCommunity.orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMUNITY_NOT_FOUND));
    }

    public Community findVerifiedCommunity(Long communityId) {
        Optional<Community> optionalCommunity = communityRepository.findById(communityId);
        return optionalCommunity.orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMUNITY_NOT_FOUND));
    }
}

