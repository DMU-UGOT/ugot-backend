package com.dmuIt.domain.service;

import com.dmuIt.domain.dto.GroupDto;
import com.dmuIt.domain.entity.Community;
import com.dmuIt.domain.entity.Group;
import com.dmuIt.domain.entity.Member;
import com.dmuIt.domain.entity.Study;
import com.dmuIt.domain.repository.GroupRepository;
import com.dmuIt.global.exception.BusinessLogicException;
import com.dmuIt.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final MemberService memberService;
    public void createGroup(GroupDto params) {
        Group group = Group.builder().
                groupName(params.getGroupName()).
                person(params.getPerson()).
                githubUrl(params.getGithubUrl()).
                build();
        groupRepository.save(group);

    }

    public List<GroupDto> findAll() {
        List<Group> list = groupRepository.findAll();
        return list.stream().map(GroupDto::new).collect(Collectors.toList());
    }

    @Transactional
    public void deleteGroup(final Long id) {
        Group entity = groupRepository.findById(id).orElseThrow();
        groupRepository.delete(entity);
    }

}
