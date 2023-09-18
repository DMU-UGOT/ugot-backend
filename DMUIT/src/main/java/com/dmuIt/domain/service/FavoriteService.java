package com.dmuIt.domain.service;

import com.dmuIt.domain.dto.FavoriteDto;
import com.dmuIt.domain.dto.GroupDto;
import com.dmuIt.domain.entity.*;
import com.dmuIt.domain.repository.FavoriteRepository;
import com.dmuIt.domain.repository.GroupRepository;
import com.dmuIt.domain.repository.MemberRepository;
import com.dmuIt.global.exception.BusinessLogicException;
import com.dmuIt.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final GroupRepository groupRepository;
    @Transactional
    public List<FavoriteDto> findAll(){
        List<Favorite> list = favoriteRepository.findAll();
        return list.stream().map(FavoriteDto::new).collect(Collectors.toList());
    }


    public void addLike(final Long groupid, final Favorite favorite) {
        Optional<Group> item = groupRepository.findByGroupId(groupid);
        favorite.Like(item.get());
        favoriteRepository.save(favorite);
    }

    @Transactional
    public void deleteLike(/*String loginId,*/ Long groupId) {
        Group group = groupRepository.findByGroupId(groupId).get();
        favoriteRepository.deleteByLikeId(/*loginId, */groupId);
    }

/*    public Boolean checkLike(String loginId, Long boardId) {
        return favoriteRepository.existsByUserLoginIdAndBoardId(loginId, boardId);
    }*/


}