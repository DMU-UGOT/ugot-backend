package com.dmuIt.domain.entity;

import com.dmuIt.global.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "_group")
public class Group extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;

    @Column(length = 30, nullable = false)
    private String groupName;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private Integer allPersonnel;

    @Column(nullable = false)
    private Integer nowPersonnel = 1;

    @Column(length = 50, nullable = false)
    private String githubUrl;

    @JsonIgnore
    @OneToMany(mappedBy = "group", cascade = CascadeType.PERSIST)
    private List<MemberGroup> memberGroups = new ArrayList<>();


    @Builder
    public Group(Long groupId, String groupName, String content, Integer allPersonnel, String githubUrl) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.content = content;
        this.allPersonnel = allPersonnel;
        this.githubUrl = githubUrl;
    }
}
