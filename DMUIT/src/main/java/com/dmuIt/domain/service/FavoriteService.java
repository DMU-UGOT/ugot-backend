package com.dmuIt.domain.service;

import com.dmuIt.domain.entity.Favorite;
import com.dmuIt.domain.entity.Group;
import com.dmuIt.domain.entity.Member;
import com.dmuIt.domain.repository.FavoriteRepository;
import com.dmuIt.domain.repository.GroupRepository;
import com.dmuIt.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final MemberRepository memberRepository;
    private final GroupRepository groupRepository;

    @Transactional
    public void addLike(/*String loginId, */Long boardId) {
        Group group = groupRepository.findById(boardId).get();
       // Member LoginMember = memberRepository.findByEmail(loginId).get();

        favoriteRepository.save(Favorite.builder()
                //.member(LoginMember)
                .group(group)
                .build());
    }

    @Transactional
    public void deleteLike(/*String loginId,*/ Long groupId) {
        Group group = groupRepository.findById(groupId).get();
        favoriteRepository.deleteByUserLoginIdAndBoardId(/*loginId, */groupId);
    }

    public Boolean checkLike(String loginId, Long boardId) {
        return favoriteRepository.existsByUserLoginIdAndBoardId(loginId, boardId);
    }




}