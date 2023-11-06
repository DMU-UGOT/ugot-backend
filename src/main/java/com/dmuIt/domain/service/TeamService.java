package com.dmuIt.domain.service;

import com.dmuIt.domain.dto.SearchHistoryDto;
import com.dmuIt.domain.entity.*;
import com.dmuIt.domain.repository.MemberGroupRepository;
import com.dmuIt.domain.repository.TeamBookmarkRepository;
import com.dmuIt.domain.repository.TeamRepository;
import com.dmuIt.domain.repository.SearchHistoryRepository;
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
public class TeamService {
    private final TeamRepository teamRepository;
    private final TeamBookmarkRepository bookmarkRepository;
    private final SearchHistoryRepository searchHistoryRepository;
    private final MemberService memberService;
    private final MemberGroupRepository memberGroupRepository;
    private final GroupService groupService;

    private final static String TEAM = "team";

    public void createTeam(HttpServletRequest request, Team team) {
        team.setMember(memberService.verifiedCurrentMember(request));
        teamRepository.save(team);
    }

    @Transactional
    public void updateTeam(HttpServletRequest request, Team team) {
        Team findTeam = findVerifiedTeam(team.getId());
        Member member = memberService.verifiedCurrentMember(request);
        if (findTeam.getMember().getMemberId() != member.getMemberId()) {
            throw new BusinessLogicException(ExceptionCode.NO_PERMISSION);
        }
        Optional.ofNullable(team.getTitle())
                .ifPresent(findTeam::setTitle);
        Optional.ofNullable(team.getContent())
                .ifPresent(findTeam::setContent);
        Optional.ofNullable(team.getField())
                .ifPresent(findTeam::setField);
        Optional.ofNullable(team.get_class())
                .ifPresent(findTeam::set_class);
        Optional.ofNullable(team.getGoal())
                .ifPresent(findTeam::setGoal);
        Optional.ofNullable(team.getLanguage())
                .ifPresent(findTeam::setLanguage);
        Optional.ofNullable(team.getKakaoOpenLink())
                .ifPresent(findTeam::setKakaoOpenLink);
        Optional.ofNullable(team.getGitHubLink())
                .ifPresent(findTeam::setGitHubLink);
        findTeam.setModifiedAt(LocalDateTime.now());
        teamRepository.save(findTeam);
    }

    public void removeTeam(HttpServletRequest request, long teamId) {
        Team team = findVerifiedTeam(teamId);
        Member member = memberService.verifiedCurrentMember(request);
        if (team.getMember().getMemberId() != member.getMemberId()) {
            throw new BusinessLogicException(ExceptionCode.NO_PERMISSION);
        }
        teamRepository.delete(team);
    }

    @Transactional
    public void refreshTeam(HttpServletRequest request, long teamId) {
        Team team = findVerifiedTeam(teamId);
        Member member = memberService.verifiedCurrentMember(request);
        if (team.getMember().getMemberId() != member.getMemberId()) {
            throw new BusinessLogicException(ExceptionCode.NO_PERMISSION);
        }
        team.setCreatedAt(LocalDateTime.now());
    }

    public Team findTeam(long teamId) {
        Team team = findVerifiedTeam(teamId);
        team.setViewCount(team.getViewCount() + 1);
        return teamRepository.save(team);
    }

    public Page<Team> findTeamsOrderByCreatedAt(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return teamRepository.findAllByOrderByCreatedAtDesc(pageRequest);
    }

    public Page<Team> findTeamsOrderByViewCount(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return teamRepository.findAllByOrderByViewCountDesc(pageRequest);
    }

    public Page<Team> findTeamsOrderByAllPersonnel(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return teamRepository.findAllByOrderByGroupAllPersonnelDesc(pageRequest);
    }

    public Page<Team> findTeamsOrderByAllSkill(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return teamRepository.findAllByOrderByGroupAllPersonnelDesc(pageRequest);
    }

    @Transactional
    public void saveTeamSearchKeyword(HttpServletRequest request, String keyword) {
        Member member = memberService.verifiedCurrentMember(request);
        List<SearchHistory> histories = searchHistoryRepository.findAllByMemberAndTypeOrderByCreatedAtDesc(member, TEAM);
        for (SearchHistory history : histories) {
            if (history.getKeyword().equals(keyword)) {
                history.setCreatedAt(LocalDateTime.now());
                return;
            }
        }
        SearchHistory searchHistory = SearchHistory.of(keyword, TEAM, member);
        searchHistoryRepository.save(searchHistory);
    }

    public List<SearchHistoryDto> getSearchHistory(HttpServletRequest request) {
        Member member = memberService.verifiedCurrentMember(request);
        List<SearchHistory> histories = searchHistoryRepository.findAllByMemberAndTypeOrderByCreatedAtDesc(member, TEAM);
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
        List<SearchHistory> histories = searchHistoryRepository.findAllByMemberAndTypeOrderByCreatedAtDesc(member, TEAM);
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
        searchHistoryRepository.deleteAllByMemberAndType(member, TEAM);
    }

    public List<Team> findMyTeams(HttpServletRequest request) {
        Member member = memberService.verifiedCurrentMember(request);
        return teamRepository.findTeamsByMember(member);
    }

    public List<MemberGroup> findMembers(long teamId) {
        Team team = findVerifiedTeam(teamId);
        return memberGroupRepository.findMemberGroupsByGroup(groupService.verifiedGroup(team.getGroup().getGroupId()));
    }

    @Transactional
    public void bookmarkTeam(HttpServletRequest request, long teamId) {
        Team team = findVerifiedTeam(teamId);

        Member member = memberService.verifiedCurrentMember(request);

        if (bookmarkRepository.findByTeamAndMember(team, member) == null) {
            team.setBookmarked(team.getBookmarked() + 1);
            TeamBookmark bookmark = new TeamBookmark(team, member);
            bookmarkRepository.save(bookmark);
        } else {
            TeamBookmark bookmark = bookmarkRepository.findBookmarkByTeam(team);
            bookmark.unBookmark(team);
            bookmarkRepository.delete(bookmark);
        }
    }


    public List<Team> findMyTeamBookmarks(HttpServletRequest request) {
        Member member = memberService.verifiedCurrentMember(request);
        List<TeamBookmark> teamBookmarksByMember = bookmarkRepository.findTeamBookmarksByMember(member);
        List<Team> teams = new ArrayList<>();
        for (int i = 0; i < teamBookmarksByMember.size(); i++) {
            teams.add(teamBookmarksByMember.get(i).getTeam());
        }
        return teams;
    }

    public Team findVerifiedTeam(long teamId) {
        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        Team findTeam = optionalTeam.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.TEAM_NOT_FOUND));
        return findTeam;
    }
}
