package com.dmuIt.domain.service;

import com.dmuIt.domain.entity.Member;
import com.dmuIt.domain.entity.Study;
import com.dmuIt.domain.entity.StudyBookmark;
import com.dmuIt.domain.repository.StudyBookmarkRepository;
import com.dmuIt.domain.repository.StudyRepository;
import com.dmuIt.global.exception.BusinessLogicException;
import com.dmuIt.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudyService {
    private final StudyRepository studyRepository;
    private final StudyBookmarkRepository bookmarkRepository;
    private final MemberService memberService;

    public void createStudy(Study study) {
        studyRepository.save(study);
    }

    @Transactional
    public void updateStudy(Study study) {
        Study findStudy = findVerifiedStudy(study.getStudyId());
        Optional.ofNullable(study.getTitle())
                .ifPresent(findStudy::setTitle);
        Optional.ofNullable(study.getContent())
                .ifPresent(findStudy::setContent);
        Optional.ofNullable(study.getIsContact())
                .ifPresent(findStudy::setIsContact);
        Optional.ofNullable(study.getAllPersonnel())
                .ifPresent(findStudy::setAllPersonnel);
        Optional.ofNullable(study.getKakaoOpenLink())
                .ifPresent(findStudy::setKakaoOpenLink);
        Optional.ofNullable(study.getGitHubLink())
                .ifPresent(findStudy::setGitHubLink);
        findStudy.setModifiedAt(LocalDateTime.now());
        studyRepository.save(findStudy);
    }

    public void deleteStudy(long studyId) {
        studyRepository.delete(findVerifiedStudy(studyId));
    }

    public Study findStudy(long studyId) {
        Study study = findVerifiedStudy(studyId);
        study.setViewCount(study.getViewCount() + 1);
        return studyRepository.save(study);
    }

    public Page<Study> findStudies(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return studyRepository.findAllByOrderByStudyIdDesc(pageRequest);
    }

    @Transactional
    public void bookmarkStudy(long studyId, long memberId) {
        Study study = findVerifiedStudy(studyId);

        Member member = memberService.findVerifiedMember(memberId);

        if (bookmarkRepository.findByStudyAndMember(study, member) == null) {
            study.setBookmarked(study.getBookmarked() + 1);
            StudyBookmark bookmark = new StudyBookmark(study, member);
            bookmarkRepository.save(bookmark);
        } else {
            StudyBookmark bookmark = bookmarkRepository.findBookmarkByStudy(study);
            bookmark.unBookmark(study);
            bookmarkRepository.delete(bookmark);
        }
    }

    public Study findVerifiedStudy(long studyId) {
        Optional<Study> optionalStudy = studyRepository.findById(studyId);
        Study findStudy = optionalStudy.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.STUDY_NOT_FOUND));
        return findStudy;
    }
}
