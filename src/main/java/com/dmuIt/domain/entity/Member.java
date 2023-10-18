package com.dmuIt.domain.entity;

import com.dmuIt.global.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "member")
public class Member extends Auditable {
    //필드
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(length = 15, nullable = false)
    private String name;

    @Column(length = 30, nullable = false)
    private String nickname;

    @Column(length = 50, updatable = false, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 20, nullable = false)
    private String major;

    @Column(length = 21, nullable = false)
    private Integer grade;

    @Column(length = 10, nullable = false)
    private String _class;

    @Column(nullable = false)
    private String gitHubLink;

    @Column(nullable = false)
    private String personalBlogLink;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    @ElementCollection
    private List<String> skill = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "member") // 일대다관계
    private List<ClassChange> classChanges;

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<TeamBookmark> teamBookmarks;

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<StudyBookmark> studyBookmarks;

    @OneToMany(mappedBy = "member")
    private List<MemberGroup> memberGroups = new ArrayList<>();

    public Member(Long memberId, String name, String nickname, String email, String password, String major, Integer grade, String _class) {
        this.memberId = memberId;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.major = major;
        this.grade = grade;
        this._class = _class;
    }
}