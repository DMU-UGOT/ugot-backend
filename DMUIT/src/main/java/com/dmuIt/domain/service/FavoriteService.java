package com.dmuIt.domain.service;

import com.dmuIt.domain.dto.CommentDto;
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
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final MemberRepository memberRepository;
    private final GroupRepository groupRepository;
    private Member member;


    public void heart(FavoriteDto favoriteDto) throws IOException {

        Favorite favorite = Favorite.builder()
                .member(memberRepository.findById(favoriteDto.getMemberId()).get())
                .group(groupRepository.findById(favoriteDto.getGroupId()).get())
                .build();
        favoriteRepository.save(favorite);
    }

    public void unHeart(FavoriteDto favoriteDto) {

        Optional<Favorite> heartOpt = findHeartWithUserAndCampaignId(favoriteDto);

        favoriteRepository.delete(heartOpt.get());
    }


    public Optional<Favorite> findHeartWithUserAndCampaignId(FavoriteDto heartDto) {
        return favoriteRepository
                .findHeartByUserAndCampaignId(member, heartDto.getGroupId().toString());
    }




}