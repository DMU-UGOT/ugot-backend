package com.dmuIt.domain.entity;

import com.dmuIt.global.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "team")
public class Team extends Auditable {
    //필드
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id", unique = true, nullable = false)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 50, nullable = false)
    private String content;

    @Column(length = 20, nullable = false)
    private String field;

    @Column(length = 20, nullable = false)
    private String _class;

    @Column(nullable = false)
    private String goal;

    @Column(nullable = false)
    private String language;

    @Column(nullable = false)
    private String kakaoOpenLink;

    @Column(nullable = false)
    private String gitHubLink;

    @Column
    private long viewCount = 0;

    @Column
    private long bookmarked = 0;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)// 다대일 관계
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

}