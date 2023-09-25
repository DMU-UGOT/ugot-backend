package com.dmuIt.domain.service;

import com.dmuIt.domain.entity.Favorite;
import com.dmuIt.domain.entity.Group;
import com.dmuIt.domain.entity.Member;
import com.dmuIt.domain.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final GroupService groupService;
    private final MemberService memberService;

    public void addFavorites(HttpServletRequest request, long groupId) {
        Member member = memberService.verifiedCurrentMember(request);
        Group group = groupService.verifiedGroup(groupId);
        Favorite favorite1 = favoriteRepository.findFavoriteByMemberAndGroup(member, group);
        if (favorite1 != null) {
            favoriteRepository.delete(favorite1);
        } else {
            Favorite favorite = new Favorite();
            favorite.setMember(member);
            favorite.setGroup(group);
            favoriteRepository.save(favorite);
        }
    }

    public List<Favorite> myFavorites(HttpServletRequest request) {
        return favoriteRepository.findFavoritesByMember(memberService.verifiedCurrentMember(request));
    }
}