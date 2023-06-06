package com.dmuIt.domain.member;

import com.dmuIt.domain.classChange.ClassChange;
import com.dmuIt.domain.project.Project;
import com.dmuIt.domain.vote.Vote;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;


@Getter
@NoArgsConstructor
@Entity
@Table(name = "member")
public class Member {
    //필드
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "member_id", unique = true, nullable = false)
    private Long id;

    @Column(length = 15, nullable = false)
    private String name;
/*
    @Column(length = 30, nullable = false)
    private String nickname;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 21, nullable = false)
    private String phone;

    @Column(length = 20, nullable = false)
    private String major;

    @Column(length = 21, nullable = false)
    private Integer grade;

    @Column(length = 10, nullable = false)
    private String _class;

    @ElementCollection
    @Column(length = 20, nullable = false)
    private Set<String> skill = new HashSet<>();

    @Column(length = 30, nullable = false)
    private String role;

    @Column(length = 30, nullable = false)
    private String status;

    @Column(length = 30, nullable = false)
    private Date create_at;

    @Column(length = 30, nullable = false)
    private Date modified_at;

    @OneToMany(mappedBy = "member") // 일대다관계
    private List<ClassChange> classChange;
    private List<Vote> vote;

    @ManyToMany                     // 다대다관계
    @JoinTable(name = "member_project",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    private List<Project> project;
    
    
    //빌더
    @Builder
    public Member(Long id, String name, String nickname, String email, String password,
                  String phone, String major, Integer grade, String _class, Set<String> skill,
                  String role, String status, Date create_at, Date modified_at, List<ClassChange> classChange,
                  List<Vote> vote, List<Project> project) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.major = major;
        this.grade = grade;
        this._class = _class;
        this.skill = skill;
        this.role = role;
        this.status = status;
        this.create_at = create_at;
        this.modified_at = modified_at;
        this.classChange = classChange;
        this.vote = vote;
        this.project = project;
    }*/

    @Builder

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}