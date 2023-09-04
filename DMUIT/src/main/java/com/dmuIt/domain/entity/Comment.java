package com.dmuIt.domain.entity;
import com.dmuIt.global.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment extends Auditable {
    //필드
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "comment_id", unique = true, nullable = false)
    private Long id;

    @Column(length = 50, nullable = false)
    private String content;

    @Column(length = 10, nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id")
    private Community community;

    /*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    */

    public Comment(Long id, String content, String status, Member member, Community community) {
        this.id = id;
        this.content = content;
        this.status = status;
        this.member = member;
        this.community = community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }

    public void update(String content, String status) {
        this.content = content;
        this.status = status;
    }


}
