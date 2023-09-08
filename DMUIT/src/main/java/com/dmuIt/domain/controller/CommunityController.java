package com.dmuIt.domain.controller;

import com.dmuIt.domain.dto.*;
import com.dmuIt.domain.entity.Community;
import com.dmuIt.domain.mapper.CommunityMapper;
import com.dmuIt.domain.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping("/com")
@RequiredArgsConstructor

public class CommunityController
{
    private final CommunityService communityService;
    private final CommunityMapper communityMapper;

    @PostMapping
    public void save(HttpServletRequest request, @RequestBody final CommunityRequestDto params) {
        communityService.save(request, params);
    }

    @GetMapping
    public FindAllDto getCommunity(@Positive @RequestParam int page,
                                @Positive @RequestParam int size) {

        Page<Community> communityPage = communityService.findCommunity(page - 1, size);
        PageInfo pageInfo = new PageInfo(page, size, (int) communityPage.getTotalElements(), communityPage.getTotalPages());

        List<Community> coms = communityPage.getContent();
        List<CommunityResponseDto> responses = communityMapper.ComsToComResponseDtos(coms);

        return new FindAllDto(responses, pageInfo);
    }

    @GetMapping("/{id}")
    public CommunityResponseDto findById(@PathVariable final Long id) {
        return communityService.findById(id);
    }

    @PatchMapping("/{id}")
    public void update(HttpServletRequest request, @PathVariable final Long id, @RequestBody final CommunityRequestDto params) {
        communityService.update(request, id, params);
    }

    @DeleteMapping("/{id}")
    public void delete(HttpServletRequest request, @PathVariable final Long id) {
        communityService.delete(request, id);
    }
}
