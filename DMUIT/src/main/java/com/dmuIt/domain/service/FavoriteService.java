package com.dmuIt.domain.service;

import com.dmuIt.domain.dto.FavoriteDto;
import com.dmuIt.domain.entity.Group;
import com.dmuIt.domain.entity.Member;
import com.dmuIt.domain.repository.FavoriteRepository;
import com.dmuIt.domain.repository.GroupRepository;
import com.dmuIt.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final MemberRepository memberRepository;
    private final GroupRepository groupRepository;
    public FavoriteDto goodCrew(Long crewId, Long userName){
        Group group = memberRepository.findById(userName).orElseThrow(()->new AppExce);
        Crew crew = crewRepository.findById(crewId).orElseThrow(()->new AppException(ErrorCode.CREW_NOT_FOUND,ErrorCode.CREW_NOT_FOUND.getMessage()));
        LikeResponse goodResponse = new LikeResponse();
        if(user.getLikes().stream().anyMatch(like -> like.getCrew().equals(crew))){
            likeRepository.deleteByUserAndCrew(user,crew);
            goodResponse.setMessage("좋아요 취소");
        } else {
            likeRepository.save(Like.builder().crew(crew).user(user).build());
            goodResponse.setMessage("좋아요 성공");
        }
        return goodResponse;
    }

}