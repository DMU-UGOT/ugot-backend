package com.dmuIt.domain.controller;
import com.dmuIt.domain.dto.*;
import com.dmuIt.domain.entity.Conversation;
import com.dmuIt.domain.entity.MemberGroup;
import com.dmuIt.domain.mapper.ConversationMapper;
import com.dmuIt.domain.mapper.FavoriteMapper;
import com.dmuIt.domain.mapper.GroupMapper;
import com.dmuIt.domain.mapper.MemberGroupMapper;
import com.dmuIt.domain.service.FavoriteService;
import com.dmuIt.domain.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;
    private final FavoriteService favoriteService;
    private final MemberGroupMapper memberGroupMapper;
    private final ConversationMapper conversationMapper;
    private final GroupMapper groupMapper;
    private final FavoriteMapper favoriteMapper;

//    @GetMapping
//    public List<GroupDto> findAll() {
//        return groupService.findAll();
//    }

    @PostMapping
    public void create(HttpServletRequest request, @RequestBody @Valid GroupDto params) {
        groupService.createGroup(request, params);
    }

    @PatchMapping("/{group-id}")
    public void update(HttpServletRequest request, @RequestBody @Valid GroupDto params,
                       @PathVariable("group-id") long groupId) {
        groupService.updateGroup(request, params, groupId);
    }

    @GetMapping
    public List<MemberGroupDto.GroupResponse> findMyGroups(HttpServletRequest request) {
        List<MemberGroup> groups = groupService.findMyGroups(request);
        return memberGroupMapper.memberGroupsToMemberGroupResponseDtos(groups);
    }

    @GetMapping("/{group-id}")
    public GroupDto.Response groupDetailPage(@PathVariable("group-id") long groupId) {
        return groupMapper.groupToResponse(groupService.groupDetailPage(groupId));
    }

    @GetMapping("/{group-id}/findMembers")
    public List<MemberGroupDto.MemberResponse> findMembers(@PathVariable("group-id") long groupId) {
        return memberGroupMapper.membersToMemberResponse(groupService.findMembers(groupId));
    }

    @PostMapping("/{group-id}/join")
    public void joinGroup(HttpServletRequest request, @PathVariable("group-id") long groupId) {
        groupService.joinGroup(request, groupId);
    }

    @DeleteMapping("/{group-id}/quit")
    public void quitGroup(HttpServletRequest request, @PathVariable("group-id") long groupId) {
        groupService.quitGroup(request, groupId);
    }

    @DeleteMapping("/{group-id}")
    public void deleteGroup(HttpServletRequest request, @PathVariable("group-id") long groupId) {
        groupService.deleteGroup(request, groupId);
    }

    @PostMapping("/{group-id}/notice")
    public void createNotice(HttpServletRequest request, @PathVariable("group-id") long groupId, @RequestBody @Valid NoticeDto.Post noticePostDto) {
        groupService.createNotice(request, noticePostDto, groupId);
    }

    @PatchMapping("/{group-id}/{notice-id}")
    public void updateNotice(HttpServletRequest request, @PathVariable("group-id") long groupId,
                             @PathVariable("notice-id") long noticeId, @RequestBody @Valid NoticeDto.Post noticePatchDto) {
        groupService.updateNotice(request, noticePatchDto, groupId, noticeId);
    }

    @GetMapping("/{group-id}/findNotices")
    public List<NoticeDto.Response> getNotices(@PathVariable("group-id") long groupId) {
        return groupService.getNotices(groupId);
    }

    @DeleteMapping("/{notice-id}/deleteNotice")
    public void deleteNotice(@PathVariable("notice-id") long noticeId) {
        groupService.deleteNotice(noticeId);
    }

    @PostMapping("/{group-id}/conversation")
    public void createConversation(HttpServletRequest request,
                                   @PathVariable("group-id") long groupId,
                                   @RequestBody @Valid ConversationDto.Post postConversationDto) {
        Conversation conversation = conversationMapper.conversationPostDtoToConversation(postConversationDto);
        groupService.createConversation(request, groupId, conversation);
    }

    @GetMapping("/{group-id}/conversations")
    public List<ConversationDto.Response> findConversations(HttpServletRequest request, @PathVariable("group-id") long groupId) {
        return conversationMapper.conversationsToResponse(groupService.findConversation(request, groupId));
    }

    @DeleteMapping("/{conversation-id}/deleteConversation")
    public void deleteConversation(HttpServletRequest request, @PathVariable("conversation-id") long conversationId) {
        groupService.deleteConversation(request, conversationId);
    }

    @PostMapping("/{group-id}/addFavorites")
    public void createFavorites(HttpServletRequest request, @PathVariable("group-id") long groupId) {
        favoriteService.addFavorites(request, groupId);
    }

    @GetMapping("/myFavorites")
    public List<FavoriteDto.Response> myFavorites(HttpServletRequest request) {
        return favoriteMapper.favoritesToResponses(favoriteService.myFavorites(request));
    }
}
