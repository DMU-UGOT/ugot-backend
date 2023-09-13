package com.dmuIt.domain.service;

import com.dmuIt.domain.entity.Member;
import com.dmuIt.domain.entity.Team;
import com.dmuIt.domain.entity.TeamBookmark;
import com.dmuIt.domain.repository.TeamBookmarkRepository;
import com.dmuIt.domain.repository.TeamRepository;
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
    private final MemberService memberService;

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
                .ifPresent(title -> findTeam.setTitle(title));
        Optional.ofNullable(team.getContent())
                .ifPresent(content -> findTeam.setContent(content));
        Optional.ofNullable(team.getField())
                .ifPresent(field -> findTeam.setField(field));
        Optional.ofNullable(team.get_class())
                .ifPresent(_class -> findTeam.set_class(_class));
        Optional.ofNullable(team.getAllPersonnel())
                .ifPresent(allPersonnel -> findTeam.setAllPersonnel(allPersonnel));
        Optional.ofNullable(team.getKakaoOpenLink())
                .ifPresent(kakaoOpenLink -> findTeam.setKakaoOpenLink(kakaoOpenLink));
        Optional.ofNullable(team.getGitHubLink())
                .ifPresent(gitHubLink -> findTeam.setGitHubLink(gitHubLink));
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

    public Team findTeam(long teamId) {
        Team team = findVerifiedTeam(teamId);
        team.setViewCount(team.getViewCount() + 1);
        return teamRepository.save(team);
    }

    public Page<Team> findTeams(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return teamRepository.findAllByOrderByIdDesc(pageRequest);
    }

    public List<Team> findMyTeams(HttpServletRequest request) {
        Member member = memberService.verifiedCurrentMember(request);
        return teamRepository.findTeamsByMember(member);
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
