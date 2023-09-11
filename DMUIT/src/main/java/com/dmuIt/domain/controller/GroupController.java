package com.dmuIt.domain.controller;
import com.dmuIt.domain.dto.GroupDto;
import com.dmuIt.domain.entity.Group;
import com.dmuIt.domain.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupController {
    private GroupService groupService;
    @GetMapping
    public List<GroupDto> findAll() {
        return groupService.findAll();
    }

    @PostMapping
    public void create(@RequestBody @Valid GroupDto params) {
        groupService.createGroup(params);
    }


}
