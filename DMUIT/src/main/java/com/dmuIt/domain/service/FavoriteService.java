package com.dmuIt.domain.service;

import com.dmuIt.domain.dto.FavoriteDto;
import com.dmuIt.domain.dto.GroupDto;
import com.dmuIt.domain.entity.Community;
import com.dmuIt.domain.entity.Favorite;
import com.dmuIt.domain.entity.Group;
import com.dmuIt.domain.entity.Member;
import com.dmuIt.domain.repository.FavoriteRepository;
import com.dmuIt.domain.repository.GroupRepository;
import com.dmuIt.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final MemberRepository memberRepository;
    private final GroupRepository groupRepository;


    @Transactional
    public List<FavoriteDto> findAll(){
        List<Favorite> list = favoriteRepository.findAll();
        return list.stream().map(FavoriteDto::new).collect(Collectors.toList());
    }

    @Transactional
    public void addLike(/*String loginId, */final Long id, final Favorite favorite) {
        Optional<Group> item = groupRepository.findById(id);
        favorite.setGroup(item.get());

       // Member LoginMember = memberRepository.findByEmail(loginId).get();

        favoriteRepository.save(favorite);
    }

    @Transactional
    public void deleteLike(/*String loginId,*/ Long groupId) {
        Group group = groupRepository.findById(groupId).get();
        favoriteRepository.deleteByGroupLikeId(/*loginId, */groupId);
    }

/*    public Boolean checkLike(String loginId, Long boardId) {
        return favoriteRepository.existsByUserLoginIdAndBoardId(loginId, boardId);
    }*/




}