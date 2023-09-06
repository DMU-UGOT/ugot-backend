package com.dmuIt.domain.controller;

import com.dmuIt.domain.dto.CommunityRequestDto;
import com.dmuIt.domain.dto.CommunityResponseDto;
import com.dmuIt.domain.service.CommunityService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public void save(HttpServletRequest request, @RequestBody final CommunityRequestDto params) {
        communityService.save(request, params);
    }

    /**
     * 게시글 리스트 조회
     */
    @GetMapping
    public List<CommunityResponseDto> findAll() {
        return communityService.findAll();
    }

    /**
     * 게시글 상세정보 조회
     */
    @GetMapping("/{id}")
    public CommunityResponseDto findById(@PathVariable final Long id) {
        return communityService.findById(id);
    }

    /**
     * 게시글 수정
     */
    @PatchMapping("/{id}")
    public void update(HttpServletRequest request, @PathVariable final Long id, @RequestBody final CommunityRequestDto params) {
        communityService.update(request, id, params);
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/{id}")
    public void delete(HttpServletRequest request, @PathVariable final Long id) {
        communityService.delete(request, id);
    }
}
