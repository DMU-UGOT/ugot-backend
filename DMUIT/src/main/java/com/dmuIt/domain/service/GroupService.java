package com.dmuIt.domain.service;

import com.dmuIt.domain.dto.GroupDto;
import com.dmuIt.domain.dto.MemberDto;
import com.dmuIt.domain.dto.MemberGroupDto;
import com.dmuIt.domain.dto.NoticeDto;
import com.dmuIt.domain.entity.*;
import com.dmuIt.domain.mapper.GroupMapper;
import com.dmuIt.domain.mapper.NoticeMapper;
import com.dmuIt.domain.repository.ConversationRepository;
import com.dmuIt.domain.repository.GroupRepository;
import com.dmuIt.domain.repository.MemberGroupRepository;
import com.dmuIt.domain.repository.NoticeRepository;
import com.dmuIt.global.exception.BusinessLogicException;
import com.dmuIt.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final NoticeRepository noticeRepository;
    private final MemberGroupRepository memberGroupRepository;
    private final ConversationRepository conversationRepository;
    private final MemberService memberService;
    private final NoticeMapper noticeMapper;
    private final GroupMapper groupMapper;

    public void createGroup(HttpServletRequest request, GroupDto params) {
        Member member = memberService.verifiedCurrentMember(request);
        Group group = Group.builder().
                groupName(params.getGroupName()).
                content(params.getContent()).
                allPersonnel(params.getAllPersonnel()).
                githubUrl(params.getGithubUrl()).
                build();
        group.setNickname(member.getNickname());
        MemberGroup memberGroup = new MemberGroup();
        memberGroup.setMember(member);
        memberGroup.setGroup(group);
        List<MemberGroup> memberGroups = new ArrayList<>();
        memberGroups.add(memberGroup);
        group.setMemberGroups(memberGroups);
        groupRepository.save(group);
    }

//    public List<GroupDto> findAll() {
//        List<Group> list = groupRepository.findAll();
//        return list.stream().map(GroupDto::new).collect(Collectors.toList());
//    }

    public Group groupDetailPage(long groupId) {
        return verifiedGroup(groupId);
    }

    public List<MemberGroup> findMyGroups(HttpServletRequest request) {
        Member member = memberService.verifiedCurrentMember(request);
        return memberGroupRepository.findMemberGroupsByMember(member);
    }

    @Transactional
    public void joinGroup(HttpServletRequest request, long groupId) {
        Member member = memberService.verifiedCurrentMember(request);
        List<MemberGroup> members = findMembers(groupId);
        for (MemberGroup value : members) {
            if (value.getMember().equals(member)) {
                throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
            }
        }
        Group group = verifiedGroup(groupId);
        if (group.getNowPersonnel() == group.getAllPersonnel()) {
            throw new BusinessLogicException(ExceptionCode.GROUP_IS_FULL);
        }
        group.setNowPersonnel(group.getNowPersonnel() + 1);
        MemberGroup memberGroup = new MemberGroup();
        memberGroup.setMember(member);
        memberGroup.setGroup(group);
        memberGroupRepository.save(memberGroup);
    }

    @Transactional
    public void quitGroup(HttpServletRequest request, long groupId) {
        Member member = memberService.verifiedCurrentMember(request);
        List<MemberGroup> members = findMembers(groupId);
        int cnt = 0;
        for (MemberGroup value : members) {
            if (value.getMember().equals(member)) {
                cnt++;
            }
        }
        if (cnt != 1) {
            throw new BusinessLogicException(ExceptionCode.NO_PERMISSION);
        }
        Group group = verifiedGroup(groupId);
        group.setNowPersonnel(group.getNowPersonnel() - 1);
        memberGroupRepository.delete(memberGroupRepository.findMemberGroupByMember(member));
    }

    public List<MemberGroup> findMembers(long groupId) {
        return memberGroupRepository.findMemberGroupsByGroup(verifiedGroup(groupId));
    }

    @Transactional
    public void deleteGroup(HttpServletRequest request, final Long groupId) {
        Member member = memberService.verifiedCurrentMember(request);
        MemberGroup memberGroup = memberGroupRepository.findMemberGroupByGroup(verifiedGroup(groupId));
        if (member.getMemberId() != memberGroup.getMember().getMemberId()) {
            throw new BusinessLogicException(ExceptionCode.NO_PERMISSION);
        }
        groupRepository.delete(memberGroup.getGroup());
    }

    public void createNotice(NoticeDto.Post noticePostDto, long groupId) {
        Notice notice = new Notice();
        LocalDate date = LocalDate.of(noticePostDto.getYear(), noticePostDto.getMonth(), noticePostDto.getDateOfMonth());
        notice.setDate(date);
        notice.setContent(noticePostDto.getContent());
        notice.setGroup(verifiedGroup(groupId));
        noticeRepository.save(notice);
    }

    public void updateNotice(NoticeDto.Post noticePostDto, long noticeId) {
        Notice notice = verifiedNotice(noticeId);
        LocalDate date = LocalDate.of(noticePostDto.getYear(), noticePostDto.getMonth(), noticePostDto.getDateOfMonth());
        notice.setDate(date);
        notice.setContent(noticePostDto.getContent());
        noticeRepository.save(notice);
    }

    public List<NoticeDto.Response> getNotices() {
        return noticeMapper.NoticesToNoticeResponseDtos(noticeRepository.findAll());
    }

    public void deleteNotice(Long noticeId) {
        noticeRepository.delete(verifiedNotice(noticeId));
    }

    public void createConversation(HttpServletRequest request, long groupId, Conversation conversation) {
        Member member = memberService.verifiedCurrentMember(request);
        conversation.setMember(member);
        conversation.setGroup(verifiedGroup(groupId));
        conversationRepository.save(conversation);
    }

    public List<Conversation> findConversation(long groupId) {
        return conversationRepository.findConversationByGroup(verifiedGroup(groupId));
    }

    public void deleteConversation(HttpServletRequest request, long conversationId) {
        Member member = memberService.verifiedCurrentMember(request);
        Conversation conversation = verifiedConversation(conversationId);
        if (member.getMemberId() != conversation.getMember().getMemberId()) {
            throw new BusinessLogicException(ExceptionCode.NO_PERMISSION);
        }
        conversationRepository.delete(verifiedConversation(conversationId));
    }

    public Group verifiedGroup(Long groupId) {
        Optional<Group> optionalGroup = groupRepository.findById(groupId);
        return optionalGroup.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.GROUP_NOT_FOUND));
    }

    public Notice verifiedNotice(Long noticeId) {
        Optional<Notice> optionalNotice = noticeRepository.findById(noticeId);
        return optionalNotice.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.NOTICE_NOT_FOUND));
    }

    public Conversation verifiedConversation(Long conversationId) {
        Optional<Conversation> optionalConversation = conversationRepository.findById(conversationId);
        return optionalConversation.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.CONVERSATION_NOT_FOUND));
    }
}
