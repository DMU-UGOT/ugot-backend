package com.dmuIt.domain.service;

import com.dmuIt.domain.dto.CommunityRequestDto;
import com.dmuIt.domain.dto.CommunityResponseDto;
import com.dmuIt.domain.entity.Community;
import com.dmuIt.domain.entity.Team;
import com.dmuIt.domain.repository.CommunityRepository;
import com.dmuIt.global.exception.BusinessLogicException;
import com.dmuIt.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;

    @Transactional
    public Long save(final CommunityRequestDto params) {
        Community entity = communityRepository.save(params.toEntity());
        return entity.getId();
    }
    @Transactional
    public List<CommunityResponseDto> findAll() {
        List<Community> list = communityRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        return list.stream().map(CommunityResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public CommunityResponseDto findById(final Long id) {
        Community entity = communityRepository.findById(id).orElseThrow();
        entity.increaseViews();
        return new CommunityResponseDto(entity);
    }
    @Transactional
    public Page<Community> findCommunity(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return communityRepository.findAllByOrderByIdDesc(pageRequest);
    }

    @Transactional
    public Long update(final Long id, final CommunityRequestDto params) {

        Community entity = communityRepository.findById(id).orElseThrow();
        //Community entity = communityRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        entity.update(params.toEntity().getTitle(),params.toEntity().getContent(),params.toEntity().getMember_id());
        return id;
    }

    @Transactional
    public void delete(long id) {
        communityRepository.delete(findVerifiedCom(id));
    }

    public Community findVerifiedCom(long comId) {
        Optional<Community> optionalTeam = communityRepository.findById(comId);
        Community findCom = optionalTeam.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.TEAM_NOT_FOUND));
        return findCom;
    }

}

