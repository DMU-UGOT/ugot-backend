package com.dmuIt.domain.service;

import com.dmuIt.domain.dto.GroupDto;
import com.dmuIt.domain.entity.Group;
import com.dmuIt.domain.entity.Member;
import com.dmuIt.domain.entity.Study;
import com.dmuIt.domain.repository.GroupRepository;
import com.dmuIt.global.exception.BusinessLogicException;
import com.dmuIt.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    public void createGroup(GroupDto params) {
        Group group = Group.builder().
                id(params.getId()).
                groupName(params.getGroupName()).
                githubUrl(params.getGithubUrl()).
                build();
        groupRepository.save(group);

    }

    public List<GroupDto> findAll() {
        List<Group> list = groupRepository.findAll();
        return list.stream().map(GroupDto::new).collect(Collectors.toList());
    }

}
