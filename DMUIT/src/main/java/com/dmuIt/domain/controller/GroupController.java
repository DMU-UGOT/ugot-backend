package com.dmuIt.domain.controller;
import com.dmuIt.domain.dto.*;
import com.dmuIt.domain.entity.Conversation;
import com.dmuIt.domain.entity.MemberGroup;
import com.dmuIt.domain.mapper.ConversationMapper;
import com.dmuIt.domain.mapper.GroupMapper;
import com.dmuIt.domain.mapper.MemberGroupMapper;
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
    private final MemberGroupMapper memberGroupMapper;
    private final ConversationMapper conversationMapper;
    private final GroupMapper groupMapper;

//    @GetMapping
//    public List<GroupDto> findAll() {
//        return groupService.findAll();
//    }

    @PostMapping
    public void create(HttpServletRequest request, @RequestBody @Valid GroupDto params) {
        groupService.createGroup(request, params);
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

    @GetMapping("/findMembers/{group-id}")
    public List<MemberGroupDto.MemberResponse> findMembers(@PathVariable("group-id") long groupId) {
        return memberGroupMapper.membersToMemberResponse(groupService.findMembers(groupId));
    }

    @PostMapping("/join/{group-id}")
    public void joinGroup(HttpServletRequest request, @PathVariable("group-id") long groupId) {
        groupService.joinGroup(request, groupId);
    }

    @DeleteMapping("/quit/{group-id}")
    public void quitGroup(HttpServletRequest request, @PathVariable("group-id") long groupId) {
        groupService.quitGroup(request, groupId);
    }

    @DeleteMapping("/{group-id}")
    public void deleteGroup(HttpServletRequest request, @PathVariable("group-id") long groupId) {
        groupService.deleteGroup(request, groupId);
    }

    @PostMapping("/{group-id}/notice")
    public void createNotice(@PathVariable("group-id") long groupId, @RequestBody @Valid NoticeDto.Post noticePostDto) {
        groupService.createNotice(noticePostDto, groupId);
    }

    @PatchMapping("/{notice-id}")
    public void updateNotice(@PathVariable("notice-id") long noticeId, @RequestBody @Valid NoticeDto.Post noticePatchDto) {
        groupService.updateNotice(noticePatchDto, noticeId);
    }

    @GetMapping("/findAllNotices")
    public List<NoticeDto.Response> getNotices() {
        return groupService.getNotices();
    }

    @DeleteMapping("/deleteNotice/{notice-id}")
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
    public List<ConversationDto.Response> findConversations(@PathVariable("group-id") long groupId) {
        return conversationMapper.conversationsToResponse(groupService.findConversation(groupId));
    }

    @DeleteMapping("/deleteConversation/{conversation-id}")
    public void deleteConversation(HttpServletRequest request, @PathVariable("conversation-id") long conversationId) {
        groupService.deleteConversation(request, conversationId);
    }
}
