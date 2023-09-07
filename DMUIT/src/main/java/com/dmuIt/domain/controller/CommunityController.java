package com.dmuIt.domain.controller;

import com.dmuIt.domain.dto.*;
import com.dmuIt.domain.entity.Community;
import com.dmuIt.domain.mapper.CommunityMapper;
import com.dmuIt.domain.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;
@RestController
@RequestMapping("/com")
@RequiredArgsConstructor

public class CommunityController
{
    private final CommunityService communityService;
    private final CommunityMapper communityMapper;

    @GetMapping
    public PageDto getCommunity(@Positive @RequestParam int page,
                                @Positive @RequestParam int size) {

        Page<Community> communityPage = communityService.findCommunity(page - 1, size);
        PageInfo pageInfo = new PageInfo(page, size, (int) communityPage.getTotalElements(), communityPage.getTotalPages());

        List<Community> coms = communityPage.getContent();
        List<Community> responses = communityMapper.ComsToComResponseDtos(coms);

        return new PageDto(responses, pageInfo);
    }

    @GetMapping("/{id}")
    public CommunityResponseDto findById(@PathVariable final Long id) {
        return communityService.findById(id);
    }


    @PostMapping
    public Long save(@RequestBody final CommunityRequestDto params) {
        return communityService.save(params);
    }

    @PatchMapping("/{id}")
    public Long update(@PathVariable final Long id, @RequestBody final CommunityRequestDto params) {
        return communityService.update(id, params);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable final Long id) {
        communityService.delete(id);
    }
}
