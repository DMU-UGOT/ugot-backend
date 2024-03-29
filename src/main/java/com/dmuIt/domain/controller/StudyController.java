package com.dmuIt.domain.controller;

import com.dmuIt.domain.dto.*;
import com.dmuIt.domain.entity.Study;
import com.dmuIt.domain.mapper.MemberGroupMapper;
import com.dmuIt.domain.mapper.StudyMapper;
import com.dmuIt.domain.repository.StudyRepository;
import com.dmuIt.domain.service.GroupService;
import com.dmuIt.domain.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/studies")
@RequiredArgsConstructor
public class StudyController {
    private final StudyRepository studyRepository;
    private final StudyMapper studyMapper;
    private final StudyService studyService;
    private final GroupService groupService;
    private final MemberGroupMapper memberGroupMapper;

    @PostMapping
    public void createStudy(HttpServletRequest request, @RequestBody @Valid StudyDto.Post postStudyDto) {
        Study study = studyMapper.studyPostDtoToStudy(postStudyDto);
        study.setGroup(groupService.verifiedGroup(postStudyDto.getGroupId()));
        studyService.createStudy(request, study);
    }

    @PatchMapping("/{study-id}")
    public void updateStudy(HttpServletRequest request,
                            @RequestBody @Valid StudyDto.Patch patchStudyDto,
                            @PathVariable("study-id") long studyId) {
        Study study = studyMapper.studyPatchDtoToStudy(patchStudyDto);
        study.setStudyId(studyId);
        studyService.updateStudy(request, study);
    }

    @PatchMapping("/{study-id}/refresh")
    public void refreshStudy(HttpServletRequest request,
                            @PathVariable("study-id") long studyId) {
        studyService.refreshStudy(request, studyId);
    }

    @GetMapping("/{study-id}")
    public StudyDto.Response getStudy(@PathVariable("study-id") long studyId) {
        Study study = studyService.findStudy(studyId);
        return studyMapper.studyToResponse(study);
    }

    @GetMapping("/createdAt")
    public FindAllDto<?> getStudiesOrderByCreatedAt(@Positive @RequestParam int page,
                                    @Positive @RequestParam int size) {
        Page<Study> studyPage = studyService.findStudiesOrderByCreatedAt(page - 1, size);
        PageInfo pageInfo = getPageInfo(page, size, studyPage);

        List<Study> studies = studyPage.getContent();
        List<StudyDto.Response> responses = studyMapper.studiesToStudyResponseDtos(studies);

        return new FindAllDto<>(responses, pageInfo);
    }

    @GetMapping("/viewCount")
    public FindAllDto<?> getStudiesOrderByViewCount(@Positive @RequestParam int page,
                                    @Positive @RequestParam int size) {
        Page<Study> studyPage = studyService.findStudiesOrderByViewCount(page - 1, size);
        PageInfo pageInfo = getPageInfo(page, size, studyPage);

        List<Study> studies = studyPage.getContent();
        List<StudyDto.Response> responses = studyMapper.studiesToStudyResponseDtos(studies);

        return new FindAllDto<>(responses, pageInfo);
    }

    @GetMapping("/allPersonnel")
    public FindAllDto<?> getStudiesOrderByAllPersonnel(@Positive @RequestParam int page,
                                    @Positive @RequestParam int size) {
        Page<Study> studyPage = studyService.findStudiesOrderByAllPersonnel(page - 1, size);
        PageInfo pageInfo = getPageInfo(page, size, studyPage);

        List<Study> studies = studyPage.getContent();
        List<StudyDto.Response> responses = studyMapper.studiesToStudyResponseDtos(studies);

        return new FindAllDto<>(responses, pageInfo);
    }

    private static PageInfo getPageInfo(int page, int size, Page<Study> studyPage) {
        return new PageInfo(page, size, (int) studyPage.getTotalElements(), studyPage.getTotalPages());
    }


    @GetMapping("/myStudies")
    public List<StudyDto.Response> findMyStudies(HttpServletRequest request) {
        return studyMapper.studiesToStudyResponseDtos(studyService.findMyStudies(request));
    }

    @GetMapping("/{study-id}/findMembers")
    public List<MemberGroupDto.MemberResponse> findMembers(@PathVariable("study-id") long studyId) {
        return memberGroupMapper.membersToMemberResponse(studyService.findMembers(studyId));
    }

    @DeleteMapping("/{study-id}")
    public void deleteStudy(HttpServletRequest request, @PathVariable("study-id") long studyId) {
        studyService.deleteStudy(request, studyId);
    }

    @PostMapping("/bookmark/{study-id}")
    public void bookmarkStudy(HttpServletRequest request, @PathVariable("study-id") long studyId) {
        studyService.bookmarkStudy(request, studyId);
    }

    @GetMapping("/bookmark")
    public List<StudyDto.Response> findMyStudyBookmarks(HttpServletRequest request) {
        List<Study> myStudyBookmarks = studyService.findMyStudyBookmarks(request);
        return studyMapper.studiesToStudyResponseDtos(myStudyBookmarks);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/search")
    public Page<StudyDto> searchPaging(HttpServletRequest request, @Param("keyword") String keyword, @PageableDefault(size = 5) Pageable pageRequest) {
        studyService.saveTeamSearchKeyword(request, keyword);
        Page<StudyDto> pagingList = null;
        if(keyword.length() < 2) {
            throw new IllegalArgumentException("검색어는 두 글자 이상이어야 합니다.");
        }else{
            Page<Study> studyList = studyRepository.findAllSearch(keyword, pageRequest);
            pagingList = studyList.map(
                    study -> new StudyDto(
                            study.getStudyId(), study.getTitle(), study.getContent(),
                            study.getIsContact(),
                            study.getGroup().getAllPersonnel(), study.getGroup().getNowPersonnel(), study.getSubject(), study.getField(),
                            study.getViewCount(), study.getBookmarked(),
                            study.getKakaoOpenLink(), study.getGitHubLink(), study.getCreatedAt(), study.getMember().getMemberId()


                    ));
        }
        return pagingList;
    }

    @GetMapping("/searchHistory")
    public List<SearchHistoryDto> getSearchHistory(HttpServletRequest request) {
        return studyService.getSearchHistory(request);
    }

    @DeleteMapping("/searchHistory/{keyword}")
    public void removeSearchHistory(HttpServletRequest request, @PathVariable("keyword") String keyword) {
        studyService.removeSearchHistory(request, keyword);
    }

    @DeleteMapping("/searchHistory")
    public void removeAllSearchHistory(HttpServletRequest request) {
        studyService.removeAllSearchHistory(request);
    }
}
