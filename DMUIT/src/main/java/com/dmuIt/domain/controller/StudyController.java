package com.dmuIt.domain.controller;

import com.dmuIt.domain.dto.FindAllDto;
import com.dmuIt.domain.dto.PageInfo;
import com.dmuIt.domain.dto.StudyDto;
import com.dmuIt.domain.dto.TeamDto;
import com.dmuIt.domain.entity.Study;
import com.dmuIt.domain.entity.Team;
import com.dmuIt.domain.mapper.StudyMapper;
import com.dmuIt.domain.repository.StudyRepository;
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

    @PostMapping
    public void createStudy(HttpServletRequest request, @RequestBody @Valid StudyDto.Post postStudyDto) {
        Study study = studyMapper.studyPostDtoToStudy(postStudyDto);
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

    @GetMapping("/{study-id}")
    public StudyDto.Response getStudy(@PathVariable("study-id") long studyId) {
        Study study = studyService.findStudy(studyId);
        return studyMapper.studyToResponse(study);
    }

    @GetMapping
    public FindAllDto<?> getStudies(@Positive @RequestParam int page,
                                    @Positive @RequestParam int size) {
        Page<Study> studyPage = studyService.findStudies(page - 1, size);
        PageInfo pageInfo = new PageInfo(page, size, (int) studyPage.getTotalElements(), studyPage.getTotalPages());

        List<Study> studies = studyPage.getContent();
        List<StudyDto.Response> responses = studyMapper.studiesToStudyResponseDtos(studies);

        return new FindAllDto<>(responses, pageInfo);
    }

    @GetMapping("/myStudies")
    public List<StudyDto.Response> findMyStudies(HttpServletRequest request) {
        return studyMapper.studiesToStudyResponseDtos(studyService.findMyStudies(request));
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
    public Page<StudyDto> searchPaging(@Param("keyword") String keyword, @PageableDefault(size = 5) Pageable pageRequest) {

        Page<StudyDto> pagingList = null;
        if(keyword == null) {
            Page<Study> studyList = studyRepository.findAllSearch("", pageRequest);
            pagingList = studyList.map(
                    study -> new StudyDto(
                            study.getStudyId(), study.getTitle(), study.getContent(),
                            study.getIsContact(),
                            study.getAllPersonnel(), study.getNowPersonnel(), study.getSubject(), study.getField(),
                            study.getViewCount(), study.getBookmarked(),
                            study.getKakaoOpenLink(), study.getGitHubLink(), study.getCreatedAt()

                    ));
        }else{
            Page<Study> studyList = studyRepository.findAllSearch(keyword, pageRequest);
            pagingList = studyList.map(
                    study -> new StudyDto(
                            study.getStudyId(), study.getTitle(), study.getContent(),
                            study.getIsContact(),
                            study.getAllPersonnel(), study.getNowPersonnel(), study.getSubject(), study.getField(),
                            study.getViewCount(), study.getBookmarked(),
                            study.getKakaoOpenLink(), study.getGitHubLink(), study.getCreatedAt()


                    ));
        }
        return pagingList;

    }
}
