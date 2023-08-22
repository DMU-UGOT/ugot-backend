package com.dmuIt.domain.service;

import com.dmuIt.domain.dto.CommunityRequestDto;
import com.dmuIt.domain.dto.CommunityResponseDto;
import com.dmuIt.domain.entity.Community;
import com.dmuIt.domain.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;


    /**
     * 게시글 생성
     */
    @Transactional
    public Long save(final CommunityRequestDto params) {
        Community entity = communityRepository.save(params.toEntity());
        return entity.getId();
    }

    /**
     * 게시글 리스트 조회
     */
    public List<CommunityResponseDto> findAll() {
        //Sort sort = Sort.by(Sort.Direction.DESC, "id", "created_at");
        //List<Community> list = communityRepository.findAll(sort);
        List<Community> list = communityRepository.findAll();
        return list.stream().map(CommunityResponseDto::new).collect(Collectors.toList());
    }

    /**
     * 게시글 수정
     */
    @Transactional
    public Long update(final Long id, final CommunityRequestDto params) {

        Community entity = communityRepository.findById(id).orElseThrow();
        //Community entity = communityRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        entity.update(params.toEntity().getTitle(),params.toEntity().getContent(),params.toEntity().getMember_id(),
                params.toEntity().getStatus());
        return id;
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public Long delete(final Long id) {
        Community entity = communityRepository.findById(id).orElseThrow();
        entity.delete();
        return id;
    }

}

