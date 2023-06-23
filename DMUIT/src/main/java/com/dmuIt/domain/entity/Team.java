package com.dmuIt.domain.entity;

import com.dmuIt.domain.entity.Member;
import com.dmuIt.global.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Getter
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
    private Integer personnel;

    @Column(length = 20, nullable = false)
    private Date create_at;

    @Column(length = 20, nullable = false)
    private Date modified_at;

    @ManyToOne(fetch = FetchType.LAZY)// 다대일 관계
    @JoinColumn(name = "member_id")
    private Member member;
}