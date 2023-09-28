package com.dmuIt.domain.service;

import com.dmuIt.domain.dto.GroupDto;
import com.dmuIt.domain.dto.NoticeDto;
import com.dmuIt.domain.entity.*;
import com.dmuIt.domain.mapper.NoticeMapper;
import com.dmuIt.domain.repository.*;
import com.dmuIt.global.exception.BusinessLogicException;
import com.dmuIt.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final NoticeRepository noticeRepository;
    private final MemberGroupRepository memberGroupRepository;
    private final ConversationRepository conversationRepository;
    private final ApplicationRepository applicationRepository;
    private final MemberService memberService;
    private final NoticeMapper noticeMapper;


    public void createGroup(HttpServletRequest request, GroupDto params) {
        Member member = memberService.verifiedCurrentMember(request);
        Group group = Group.builder().
                groupName(params.getGroupName()).
                content(params.getContent()).
                allPersonnel(params.getAllPersonnel()).
                githubUrl(params.getGithubUrl()).
                build();
        MemberGroup memberGroup = new MemberGroup();
        memberGroup.setMember(member);
        memberGroup.setGroup(group);
        memberGroup.setRole(MemberGroup.RoleInGroup.ADMIN);
        List<MemberGroup> memberGroups = new ArrayList<>();
        memberGroups.add(memberGroup);
        group.setMemberGroups(memberGroups);
        groupRepository.save(group);
    }

    @Transactional
    public void updateGroup(HttpServletRequest request, GroupDto params, long groupId) {
        Member member = memberService.verifiedCurrentMember(request);
        Group group = verifiedGroup(groupId);
        MemberGroup memberGroup = memberGroupRepository.findMemberGroupByMemberAndGroup(member, group);
        if (!memberGroup.getRole().equals(MemberGroup.RoleInGroup.ADMIN)) {
            throw new BusinessLogicException(ExceptionCode.NO_PERMISSION);
        }
        group.setGroupName(params.getGroupName());
        group.setContent(params.getContent());
        group.setAllPersonnel(params.getAllPersonnel());
        group.setGithubUrl(params.getGithubUrl());
        groupRepository.save(group);
    }

    public Group groupDetailPage(long groupId) {
        return verifiedGroup(groupId);
    }

    public List<MemberGroup> findMyGroups(HttpServletRequest request) {
        Member member = memberService.verifiedCurrentMember(request);
        return memberGroupRepository.findMemberGroupsByMember(member);
    }

    @Transactional
    public void applicationGroup(HttpServletRequest request, long groupId) {
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
        Application application = new Application();
        application.setMember(member);
        application.setGroup(group);
        applicationRepository.save(application);
    }

    public List<Application> getApplications(HttpServletRequest request, long groupId) {
        Member member = memberService.verifiedCurrentMember(request);
        Group group = verifiedGroup(groupId);
        MemberGroup memberGroup = memberGroupRepository.findMemberGroupByMemberAndGroup(member, group);
        if (memberGroup == null) {
            throw new BusinessLogicException(ExceptionCode.NO_PERMISSION);
        }
        if (!memberGroup.getRole().equals(MemberGroup.RoleInGroup.ADMIN)) {
            throw new BusinessLogicException(ExceptionCode.NO_PERMISSION);
        }
        return applicationRepository.findApplicationsByGroup(group);
    }

    @Transactional
    public void accept(HttpServletRequest request, long groupId, long applicationId) {
        Member member = memberService.verifiedCurrentMember(request);
        Group group = verifiedGroup(groupId);
        MemberGroup findmemberGroup = memberGroupRepository.findMemberGroupByMemberAndGroup(member, group);
        if (!findmemberGroup.getRole().equals(MemberGroup.RoleInGroup.ADMIN)) {
            throw new BusinessLogicException(ExceptionCode.NO_PERMISSION);
        }
        Application application = verifiedApplication(applicationId);
        MemberGroup memberGroup = new MemberGroup();
        memberGroup.setMember(application.getMember());
        memberGroup.setGroup(application.getGroup());
        application.getGroup().setNowPersonnel(application.getGroup().getNowPersonnel() + 1);
        memberGroupRepository.save(memberGroup);
        applicationRepository.delete(application);
    }

    @Transactional
    public void turnDown(HttpServletRequest request, long groupId, long applicationId) {
        Member member = memberService.verifiedCurrentMember(request);
        Group group = verifiedGroup(groupId);
        MemberGroup findmemberGroup = memberGroupRepository.findMemberGroupByMemberAndGroup(member, group);
        if (!findmemberGroup.getRole().equals(MemberGroup.RoleInGroup.ADMIN)) {
            throw new BusinessLogicException(ExceptionCode.NO_PERMISSION);
        }
        applicationRepository.delete(verifiedApplication(applicationId));
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
        MemberGroup memberGroup = memberGroupRepository.findMemberGroupByMemberAndGroup(member, group);
        if (memberGroup.getRole().equals(MemberGroup.RoleInGroup.ADMIN)) {
            throw new BusinessLogicException(ExceptionCode.NO_PERMISSION);
        }
        group.setNowPersonnel(group.getNowPersonnel() - 1);
        memberGroupRepository.delete(memberGroupRepository.findMemberGroupByMemberAndGroup(member, group));
    }

    @Transactional
    public void handOverAuthority(HttpServletRequest request, long groupId, long memberId) {
        Member member = memberService.verifiedCurrentMember(request);
        Group group = verifiedGroup(groupId);
        MemberGroup memberGroup = memberGroupRepository.findMemberGroupByMemberAndGroup(member, group);
        if (!memberGroup.getRole().equals(MemberGroup.RoleInGroup.ADMIN)) {
            throw new BusinessLogicException(ExceptionCode.NO_PERMISSION);
        }
        memberGroup.setRole(MemberGroup.RoleInGroup.USER);
        Member verifiedMember = memberService.findVerifiedMember(memberId);
        MemberGroup findMemberGroup = memberGroupRepository.findMemberGroupByMemberAndGroup(verifiedMember, group);
        findMemberGroup.setRole(MemberGroup.RoleInGroup.ADMIN);
    }

    @Transactional
    public void expulsion(HttpServletRequest request, long groupId, long memberId) {
        Member member = memberService.verifiedCurrentMember(request);
        Group group = verifiedGroup(groupId);
        MemberGroup memberGroup = memberGroupRepository.findMemberGroupByMemberAndGroup(member, group);
        if (!memberGroup.getRole().equals(MemberGroup.RoleInGroup.ADMIN)) {
            throw new BusinessLogicException(ExceptionCode.NO_PERMISSION);
        }
        Member findMember = memberService.findVerifiedMember(memberId);
        MemberGroup findMemberGroup = memberGroupRepository.findMemberGroupByMemberAndGroup(findMember, group);
        if (findMemberGroup.getRole().equals(MemberGroup.RoleInGroup.ADMIN)) {
            throw new BusinessLogicException(ExceptionCode.NO_PERMISSION);
        }
        memberGroupRepository.delete(findMemberGroup);
    }

    public List<MemberGroup> findMembers(long groupId) {
        return memberGroupRepository.findMemberGroupsByGroup(verifiedGroup(groupId));
    }

    @Transactional
    public void deleteGroup(HttpServletRequest request, final Long groupId) {
        Member member = memberService.verifiedCurrentMember(request);
        Group group = verifiedGroup(groupId);
        MemberGroup memberGroup = memberGroupRepository.findMemberGroupByMemberAndGroup(member, group);
        if (!memberGroup.getRole().equals(MemberGroup.RoleInGroup.ADMIN)) {
            throw new BusinessLogicException(ExceptionCode.NO_PERMISSION);
        }
        groupRepository.delete(group);
    }

    public void createNotice(HttpServletRequest request, NoticeDto.Post noticePostDto, long groupId) {
        Member member = memberService.verifiedCurrentMember(request);
        Group group = verifiedGroup(groupId);
        MemberGroup memberGroup = memberGroupRepository.findMemberGroupByMemberAndGroup(member, group);
        if (!memberGroup.getRole().equals(MemberGroup.RoleInGroup.ADMIN)) {
            throw new BusinessLogicException(ExceptionCode.NO_PERMISSION);
        }
        Notice notice = new Notice();
        LocalDate date = LocalDate.of(noticePostDto.getYear(), noticePostDto.getMonth(), noticePostDto.getDateOfMonth());
        notice.setDate(date);
        notice.setContent(noticePostDto.getContent());
        notice.setGroup(group);
        noticeRepository.save(notice);
    }

    public void updateNotice(HttpServletRequest request, NoticeDto.Post noticePostDto, long groupId, long noticeId) {
        Member member = memberService.verifiedCurrentMember(request);
        Group group = verifiedGroup(groupId);
        MemberGroup memberGroup = memberGroupRepository.findMemberGroupByMemberAndGroup(member, group);
        if (!memberGroup.getRole().equals(MemberGroup.RoleInGroup.ADMIN)) {
            throw new BusinessLogicException(ExceptionCode.NO_PERMISSION);
        }
        Notice notice = verifiedNotice(noticeId);
        LocalDate date = LocalDate.of(noticePostDto.getYear(), noticePostDto.getMonth(), noticePostDto.getDateOfMonth());
        notice.setDate(date);
        notice.setContent(noticePostDto.getContent());
        noticeRepository.save(notice);
    }

    public List<NoticeDto.Response> getNotices(long groupId) {
        return noticeMapper.NoticesToNoticeResponseDtos(noticeRepository.findNoticesByGroup(verifiedGroup(groupId)));
    }

    public void deleteNotice(Long noticeId) {
        noticeRepository.delete(verifiedNotice(noticeId));
    }

    public void createConversation(HttpServletRequest request, long groupId, Conversation conversation) {
        Member member = memberService.verifiedCurrentMember(request);
        Group group = verifiedGroup(groupId);
        if (memberGroupRepository.findMemberGroupByMemberAndGroup(member, group) == null) {
            throw new BusinessLogicException(ExceptionCode.NO_PERMISSION);
        }
        conversation.setMember(member);
        conversation.setGroup(verifiedGroup(groupId));
        conversationRepository.save(conversation);
    }

    public List<Conversation> findConversation(HttpServletRequest request, long groupId) {
        Member member = memberService.verifiedCurrentMember(request);
        Group group = verifiedGroup(groupId);
        if (memberGroupRepository.findMemberGroupByMemberAndGroup(member, group) == null) {
            throw new BusinessLogicException(ExceptionCode.NO_PERMISSION);
        }
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

    public Application verifiedApplication(Long applicationId) {
        Optional<Application> optionalApplication = applicationRepository.findById(applicationId);
        return optionalApplication.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.APPLICATION_NOT_FOUND));
    }
}
