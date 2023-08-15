package com.dmuIt.domain.controller;

import com.dmuIt.domain.dto.CommunityRequestDto;
import com.dmuIt.domain.dto.CommunityResponseDto;
import com.dmuIt.domain.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping("/com")
@RequiredArgsConstructor
public class CommunityController
{
    private final CommunityService communityService;

    /**
     * 게시글 생성
     */
    @PostMapping
    public Long save(@RequestBody final CommunityRequestDto params) {
        return communityService.save(params);
    }

    /**
     * 게시글 리스트 조회
     */
    @GetMapping("/{id}")
    public List<CommunityResponseDto> findAll() {
        return communityService.findAll();
    }

    /**
     * 게시글 수정
     */
    @PatchMapping("/{id}")
    public Long save(@PathVariable final Long id, @RequestBody final CommunityRequestDto params) {
        return communityService.update(id, params);
    }
}
