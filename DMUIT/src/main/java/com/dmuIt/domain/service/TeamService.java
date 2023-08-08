package com.dmuIt.domain.service;

import com.dmuIt.domain.entity.Bookmark;
import com.dmuIt.domain.entity.Member;
import com.dmuIt.domain.entity.Team;
import com.dmuIt.domain.repository.BookmarkRepository;
import com.dmuIt.domain.repository.TeamRepository;
import com.dmuIt.global.exception.BusinessLogicException;
import com.dmuIt.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final BookmarkRepository bookmarkRepository;
    private final MemberService memberService;

    public void createTeam(Team team) {
        teamRepository.save(team);
    }

    @Transactional
    public void updateTeam(Team team) {
        Team findTeam = findVerifiedTeam(team.getId());
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
                .ifPresent(gitHubLink -> findTeam.setKakaoOpenLink(gitHubLink));
        findTeam.setModifiedAt(LocalDateTime.now());
        teamRepository.save(findTeam);
    }

    public void removeTeam(long teamId) {
        teamRepository.delete(findVerifiedTeam(teamId));
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

    @Transactional
    public void bookmarkTeam(long teamId, long memberId) {
        Team team = findVerifiedTeam(teamId);

        Member member = memberService.findVerifiedMember(memberId);

        if (bookmarkRepository.findByTeamAndMember(team, member) == null) {
            team.setBookmarked(team.getBookmarked() + 1);
            Bookmark bookmark = new Bookmark(team, member);
            bookmarkRepository.save(bookmark);
        } else {
            Bookmark bookmark = bookmarkRepository.findBookmarkByTeam(team);
            bookmark.unBookmark(team);
            bookmarkRepository.delete(bookmark);
        }
    }

    public Team findVerifiedTeam(long teamId) {
        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        Team findTeam = optionalTeam.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.TEAM_NOT_FOUND));
        return findTeam;
    }
}
