package com.dmuIt.domain.entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {
    //필드
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "comment_id", unique = true, nullable = false)
    private Long id;

    @Column(length = 15, nullable = false)
    private String content;

    @Column(length = 10, nullable = false)
    private String status;

    @Column(length = 20, nullable = false)
    private Date create_at;

    @Column(length = 20, nullable = false)
    private Date modified_at;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id")
    private Community community;


    @Builder
    public Comment(Long id, String content, String status, Date create_at,
                   Date modified_at, Member member, Team team, Study study,
                   Community community)
    {
        this.id = id;
        this.content = content;
        this.status = status;
        this.create_at = create_at;
        this.modified_at = modified_at;
        this.member = member;
        this.team = team;
        this.study = study;
        this.community = community;
    }
}
