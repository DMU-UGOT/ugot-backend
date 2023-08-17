package com.dmuIt.domain.entity;

import com.dmuIt.global.audit.Auditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;


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

    @Column(length = 21, nullable = false)
    private String phone;

    @Column(length = 20, nullable = false)
    private String major;

    @Column(length = 21, nullable = false)
    private Integer grade;

    @Column(length = 10, nullable = false)
    private String _class;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    @ElementCollection
    private Set<String> skill = new HashSet<>();

    @OneToMany(mappedBy = "member") // 일대다관계
    private List<ClassChange> classChanges;

    @OneToMany(mappedBy = "member")
    private List<Bookmark> bookmarks;

    @ElementCollection
    private List<Long> votes;

    @ManyToMany                     // 다대다관계
    @JoinTable(name = "member_project",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    private List<Project> projects;


    
    //빌더
//    @Builder
//    public Member(Long memberId, String name, String nickname, String email, String password,
//                  String phone, String major, Integer grade, String _class, Set<String> skill,
//                  String role, String status, LocalDateTime modified_at,
//                  List<Long> votes) {
//        this.memberId = memberId;
//        this.name = name;
//        this.nickname = nickname;
//        this.email = email;
//        this.password = password;
//        this.phone = phone;
//        this.major = major;
//        this.grade = grade;
//        this._class = _class;
//        this.skill = skill;
//        this.role = role;
//        this.status = status;
//        this.modified_at = modified_at;
//        this.votes = votes;
//    }

//    @Builder
//
//    public Member(Long id, String name) {
//        this.id = id;
//        this.name = name;
//    }
}