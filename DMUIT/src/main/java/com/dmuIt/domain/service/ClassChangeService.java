package com.dmuIt.domain.service;

import com.dmuIt.domain.entity.ClassChange;
import com.dmuIt.domain.entity.Member;
import com.dmuIt.domain.repository.ClassChangeRepository;
import com.dmuIt.global.exception.BusinessLogicException;
import com.dmuIt.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClassChangeService {
    private final ClassChangeRepository classChangeRepository;
    private final MemberService memberService;

    public void create(HttpServletRequest request, ClassChange classChange) {
        classChange.setMember(memberService.verifiedCurrentMember(request));
        classChangeRepository.save(classChange);
    }

    @Transactional
    public void update(HttpServletRequest request, ClassChange classChange) {
        ClassChange findClassChange = findVerifiedClassChange(classChange.getClassChangeId());
        Member member = memberService.verifiedCurrentMember(request);
        if (findClassChange.getMember().getMemberId() != member.getMemberId()) {
            throw new BusinessLogicException(ExceptionCode.NO_PERMISSION);
        }
        Optional.ofNullable(classChange.getGrade())
                .ifPresent(findClassChange::setGrade);
        Optional.ofNullable(classChange.getCurrentClass())
                .ifPresent(findClassChange::setCurrentClass);
        Optional.ofNullable(classChange.getChangeClass())
                .ifPresent(findClassChange::setChangeClass);
        Optional.ofNullable(classChange.getStatus())
                .ifPresent(findClassChange::setStatus);
        classChange.setModifiedAt(LocalDateTime.now());
    }

    public ClassChange findClassChange(long classChangeId) {
        return findVerifiedClassChange(classChangeId);
    }

    public Page<ClassChange> findClassChanges(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return classChangeRepository.findAllByOrderByClassChangeIdDesc(pageRequest);
    }

    public List<ClassChange> findMyClassChanges(HttpServletRequest request) {
        Member member = memberService.verifiedCurrentMember(request);
        return classChangeRepository.findClassChangeByMember(member);
    }

    @Transactional
    public void delete(HttpServletRequest request, long classChangeId) {
        ClassChange classChange = findVerifiedClassChange(classChangeId);
        Member member = memberService.verifiedCurrentMember(request);
        if (classChange.getMember().getMemberId() != member.getMemberId()) {
            throw new BusinessLogicException(ExceptionCode.NO_PERMISSION);
        }
        classChangeRepository.delete(classChange);
    }

    public ClassChange findVerifiedClassChange(long classChangeId) {
        Optional<ClassChange> optionalClassChange = classChangeRepository.findById(classChangeId);
        return optionalClassChange.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.CLASS_CHANGE_NOT_FOUND));
    }
}
