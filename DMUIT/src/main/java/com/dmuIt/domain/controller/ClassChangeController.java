package com.dmuIt.domain.controller;

import com.dmuIt.domain.dto.ClassChangeDto;
import com.dmuIt.domain.dto.CommunityResponseDto;
import com.dmuIt.domain.dto.FindAllDto;
import com.dmuIt.domain.dto.PageInfo;
import com.dmuIt.domain.entity.ClassChange;
import com.dmuIt.domain.entity.Community;
import com.dmuIt.domain.mapper.ClassChangeMapper;
import com.dmuIt.domain.repository.ClassChangeRepository;
import com.dmuIt.domain.service.ClassChangeService;
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
@RequiredArgsConstructor
@RequestMapping("/classChanges")
public class ClassChangeController {
    private final ClassChangeRepository classChangeRepository;
    private final ClassChangeMapper classChangeMapper;
    private final ClassChangeService classChangeService;

    @PostMapping
    public void createClassChange(HttpServletRequest request, @RequestBody @Valid ClassChangeDto.Post postClassChangeDto) {
        ClassChange classChange = classChangeMapper.classChangePostDtoToClassChange(postClassChangeDto);
        classChangeService.create(request, classChange);
    }

    @PatchMapping("/{classChange-id}")
    public void patchClassChange(HttpServletRequest request,
                                 @RequestBody @Valid ClassChangeDto.Patch patchClassChangeDto,
                                 @PathVariable("classChange-id") long classChangeId) {
        ClassChange classChange = classChangeMapper.classChangePatchDtoToClassChange(patchClassChangeDto);
        classChange.setClassChangeId(classChangeId);
        classChangeService.update(request, classChange);
    }

    @GetMapping("/{classChange-id}")
    public ClassChangeDto.Response getClassChange(@PathVariable("classChange-id") long classChangeId) {
        ClassChange classChange = classChangeService.findClassChange(classChangeId);
        return classChangeMapper.classChangeToClassChangeResponseDto(classChange);
    }

    @GetMapping
    public FindAllDto<?> getClassChanges(@Positive @RequestParam int page,
                                         @Positive @RequestParam int size) {
        Page<ClassChange> classChangePage = classChangeService.findClassChanges(page - 1, size);
        PageInfo pageInfo = new PageInfo(page, size, (int) classChangePage.getTotalElements(), classChangePage.getTotalPages());

        List<ClassChange> classChanges = classChangePage.getContent();
        List<ClassChangeDto.Response> responses = classChangeMapper.classChangesToClassChangeResponseDtos(classChanges);

        return new FindAllDto<>(responses, pageInfo);
    }

    @GetMapping("/myClassChanges")
    public List<ClassChangeDto.Response> findMyClassChanges(HttpServletRequest request) {
        return classChangeMapper.classChangesToClassChangeResponseDtos(classChangeService.findMyClassChanges(request));
    }

    @DeleteMapping("/{classChange-id}")
    public void deleteClassChange(HttpServletRequest request, @PathVariable("classChange-id") long classChangeId) {
        classChangeService.delete(request, classChangeId);
    }

    @PatchMapping("/{classChange-id}/refresh")
    public void refreshClassChange(HttpServletRequest request,
                            @PathVariable("classChange-id") long classChangeId) {
        classChangeService.refreshClassChange(request, classChangeId);
    }
}
