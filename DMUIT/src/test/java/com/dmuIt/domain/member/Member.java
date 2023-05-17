package com.dmuIt.domain.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    //List??<------------------------------------------
    @Column(length = 21, nullable = false)
    private List skill;

    @Column(length = 30, nullable = false)
    private String role;

    @Column(length = 30, nullable = false)
    private String status;

    @Column(length = 30, nullable = false)
    private Long project_id;

    @Column(length = 30, nullable = false)
    private Date create_at;

    @Column(length = 30, nullable = false)
    private Date modified_at;

    //빌더
    @Builder
    public Member(Long id, String name, String nickname, String email, String password, String phone, String major,
                  Integer grade, String _class, List skill, String role, String status, Long project_id, Date create_at,
                  Date modified_at)
    {
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
        this.project_id = project_id;
        this.create_at = create_at;
        this.modified_at = modified_at;
    }
}