package com.dmuIt.domain.service;

import com.dmuIt.domain.dto.SearchHistoryDto;
import com.dmuIt.domain.entity.*;
import com.dmuIt.domain.repository.MemberGroupRepository;
import com.dmuIt.domain.repository.SearchHistoryRepository;
import com.dmuIt.domain.repository.StudyBookmarkRepository;
import com.dmuIt.domain.repository.StudyRepository;
import com.dmuIt.global.exception.BusinessLogicException;
import com.dmuIt.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudyService {
    private final StudyRepository studyRepository;
    private final StudyBookmarkRepository bookmarkRepository;
    private final MemberService memberService;
    private final MemberGroupRepository memberGroupRepository;
    private final GroupService groupService;
    private final SearchHistoryRepository searchHistoryRepository;

    private final static String STUDY = "study";


    public void createStudy(HttpServletRequest request, Study study) {
        study.setMember(memberService.verifiedCurrentMember(request));
        studyRepository.save(study);
    }

    @Transactional
    public void updateStudy(HttpServletRequest request, Study study) {
        Study findStudy = findVerifiedStudy(study.getStudyId());
        Member member = memberService.verifiedCurrentMember(request);
        if (findStudy.getMember().getMemberId() != member.getMemberId()) {
            throw new BusinessLogicException(ExceptionCode.NO_PERMISSION);
        }
        Optional.ofNullable(study.getTitle())
                .ifPresent(findStudy::setTitle);
        Optional.ofNullable(study.getContent())
                .ifPresent(findStudy::setContent);
        Optional.ofNullable(study.getIsContact())
                .ifPresent(findStudy::setIsContact);
        Optional.ofNullable(study.getSubject())
                .ifPresent(findStudy::setSubject);
        Optional.ofNullable(study.getField())
                .ifPresent(findStudy::setField);
        Optional.ofNullable(study.getKakaoOpenLink())
                .ifPresent(findStudy::setKakaoOpenLink);
        Optional.ofNullable(study.getGitHubLink())
                .ifPresent(findStudy::setGitHubLink);
        findStudy.setModifiedAt(LocalDateTime.now());
        studyRepository.save(findStudy);
    }

    public void deleteStudy(HttpServletRequest request, long studyId) {
        Study findStudy = findVerifiedStudy(studyId);
        Member member = memberService.verifiedCurrentMember(request);
        if (findStudy.getMember().getMemberId() != member.getMemberId()) {
            throw new BusinessLogicException(ExceptionCode.NO_PERMISSION);
        }
        studyRepository.delete(findVerifiedStudy(studyId));
    }

    @Transactional
    public void refreshStudy(HttpServletRequest request, long studyId) {
        Study study = findVerifiedStudy(studyId);
        Member member = memberService.verifiedCurrentMember(request);
        if (study.getMember().getMemberId() != member.getMemberId()) {
            throw new BusinessLogicException(ExceptionCode.NO_PERMISSION);
        }
        study.setCreatedAt(LocalDateTime.now());
    }

    public Study findStudy(long studyId) {
        Study study = findVerifiedStudy(studyId);
        study.setViewCount(study.getViewCount() + 1);
        return studyRepository.save(study);
    }

    public Page<Study> findStudiesOrderByCreatedAt(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return studyRepository.findAllByOrderByCreatedAtDesc(pageRequest);
    }

    public Page<Study> findStudiesOrderByViewCount(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return studyRepository.findAllByOrderByViewCountDesc(pageRequest);
    }

    public Page<Study> findStudiesOrderByAllPersonnel(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return studyRepository.findAllByOrderByGroupAllPersonnelDesc(pageRequest);
    }

    @Transactional
    public void saveTeamSearchKeyword(HttpServletRequest request, String keyword) {
        Member member = memberService.verifiedCurrentMember(request);
        List<SearchHistory> histories = searchHistoryRepository.findAllByMemberAndTypeOrderByCreatedAtDesc(member, STUDY);
        for (SearchHistory history : histories) {
            if (history.getKeyword().equals(keyword)) {
                history.setCreatedAt(LocalDateTime.now());
                return;
            }
        }
        SearchHistory searchHistory = SearchHistory.of(keyword, STUDY, member);
        searchHistoryRepository.save(searchHistory);
    }

    public List<SearchHistoryDto> getSearchHistory(HttpServletRequest request) {
        Member member = memberService.verifiedCurrentMember(request);
        List<SearchHistory> histories = searchHistoryRepository.findAllByMemberAndTypeOrderByCreatedAtDesc(member, STUDY);
        List<SearchHistoryDto> historyDtos = new ArrayList<>();
        for (SearchHistory history : histories) {
            historyDtos.add(SearchHistoryDto.builder()
                    .keyword(history.getKeyword())
                    .build());
        }
        return historyDtos;
    }

    @Transactional
    public void removeSearchHistory(HttpServletRequest request, String keyword) {
        Member member = memberService.verifiedCurrentMember(request);
        List<SearchHistory> histories = searchHistoryRepository.findAllByMemberAndTypeOrderByCreatedAtDesc(member, STUDY);
        for (SearchHistory history : histories) {
            if (history.getKeyword().equals(keyword)) {
                searchHistoryRepository.delete(history);
                break;
            }
        }
    }

    @Transactional
    public void removeAllSearchHistory(HttpServletRequest request) {
        Member member = memberService.verifiedCurrentMember(request);
        searchHistoryRepository.deleteAllByMemberAndType(member, STUDY);
    }

    public List<Study> findMyStudies(HttpServletRequest request) {
        Member member = memberService.verifiedCurrentMember(request);
        return studyRepository.findStudiesByMember(member);
    }

    public List<MemberGroup> findMembers(long studyId) {
        Study study = findVerifiedStudy(studyId);
        return memberGroupRepository.findMemberGroupsByGroup(groupService.verifiedGroup(study.getGroup().getGroupId()));
    }

    @Transactional
    public void bookmarkStudy(HttpServletRequest request, long studyId) {
        Study study = findVerifiedStudy(studyId);

        Member member = memberService.verifiedCurrentMember(request);

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

    public List<Study> findMyStudyBookmarks(HttpServletRequest request) {
        Member member = memberService.verifiedCurrentMember(request);
        List<StudyBookmark> studyBookmarksByMember = bookmarkRepository.findStudyBookmarksByMember(member);
        List<Study> studies = new ArrayList<>();
        for (int i = 0; i < studyBookmarksByMember.size(); i++) {
            studies.add(studyBookmarksByMember.get(i).getStudy());
        }
        return studies;
    }

    public Study findVerifiedStudy(long studyId) {
        Optional<Study> optionalStudy = studyRepository.findById(studyId);
        Study findStudy = optionalStudy.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.STUDY_NOT_FOUND));
        return findStudy;
    }
}
