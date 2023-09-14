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
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final MemberRepository memberRepository;
    private final GroupRepository groupRepository;


    @Transactional
    public List getList(){
        Community community = communityRepository.findById(id).get();
        return commentMapper.commentsToCommentResponseDtos(commentRepository.findCommentsByCommunity(community));
    }
    @Transactional
    public void insert(FavoriteDto favoriteDto) throws Exception {

        Member member = memberRepository.findById(favoriteDto.getMemberId())
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        Group group = groupRepository.findById(favoriteDto.getGroupId())
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        Favorite favorite = Favorite.builder()
                .group(group)
                .member(member)
                .build();

        favoriteRepository.save(favorite);
    }

    @Transactional
    public void delete(FavoriteDto favoriteDto) throws ChangeSetPersister.NotFoundException {

        Member member = memberRepository.findById(favoriteDto.getMemberId())
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        Group group = groupRepository.findById(favoriteDto.getGroupId())
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        Favorite favorite = favoriteRepository.findByMemberAndBoard(member, group);

        favoriteRepository.delete(favorite);
    }


}