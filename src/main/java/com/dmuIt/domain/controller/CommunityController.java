package com.dmuIt.domain.controller;

import com.dmuIt.domain.dto.*;
import com.dmuIt.domain.entity.Community;
import com.dmuIt.domain.mapper.CommunityMapper;
import com.dmuIt.domain.repository.CommunityRepository;
import com.dmuIt.domain.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import java.util.List;
@RestController
@RequestMapping("/com")
@RequiredArgsConstructor

public class CommunityController
{
    private final CommunityRepository communityRepository;
    private final CommunityService communityService;
    private final CommunityMapper communityMapper;

    @PostMapping
    public void save(HttpServletRequest request, @RequestBody final CommunityRequestDto params) {
        communityService.save(request, params);
    }

    @GetMapping
    public FindAllDto getCommunities(@Positive @RequestParam int page,
                                   @Positive @RequestParam int size) {

        Page<Community> communityPage = communityService.findCommunities(page - 1, size);
        PageInfo pageInfo = new PageInfo(page, size, (int) communityPage.getTotalElements(), communityPage.getTotalPages());

        List<Community> coms = communityPage.getContent();
        List<CommunityResponseDto> responses = communityMapper.ComsToComResponseDtos(coms);

        return new FindAllDto(responses, pageInfo);
    }

    @GetMapping("/myCommunities")
    public List<CommunityResponseDto> findMyCommunities(HttpServletRequest request) {
        return communityMapper.ComsToComResponseDtos(communityService.findMyCommunities(request));
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

    @PatchMapping("/{community-id}/refresh")
    public void refreshCommunity(HttpServletRequest request,
                            @PathVariable("community-id") long communityId) {
        communityService.refreshCommunity(request, communityId);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/search")
    public Page<CommunityResponseDto> searchPaging(HttpServletRequest request, @Param("keyword") String keyword, @PageableDefault(size = 5) Pageable pageRequest) {
        communityService.saveTeamSearchKeyword(request, keyword);
        Page<CommunityResponseDto> pagingList = null;
        if(keyword.length() < 2) {
            throw new IllegalArgumentException("검색어는 두 글자 이상이어야 합니다.");
        }else{
            Page<Community> communities = communityRepository.findAllSearch(keyword, pageRequest);
            pagingList = communities.map(
                    community -> new CommunityResponseDto(
                            community.getId(), community.getTitle(), community.getContent(),
                            community.getViewCount(), (long) community.getComments().size(),
                            community.getCreatedAt(), community.getMember().getNickname(),community.getMember().getMemberId()


                    ));
        }
        return pagingList;
    }
    @GetMapping("/searchHistory")
    public List<SearchHistoryDto> getSearchHistory(HttpServletRequest request) {
        return communityService.getSearchHistory(request);
    }

    @DeleteMapping("/searchHistory/{keyword}")
    public void removeSearchHistory(HttpServletRequest request, @PathVariable("keyword") String keyword) {
        communityService.removeSearchHistory(request, keyword);
    }

    @DeleteMapping("/searchHistory")
    public void removeAllSearchHistory(HttpServletRequest request) {
        communityService.removeAllSearchHistory(request);
    }
}
