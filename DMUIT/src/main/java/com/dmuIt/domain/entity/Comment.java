package com.dmuIt.domain.entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @Column(length = 50, nullable = false)
    private String content;

    @Column(length = 10, nullable = false)
    private String status;

    @Column
    private LocalDateTime created_at = LocalDateTime.now();

    @Column
    private LocalDateTime modified_at;


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

    public Comment(Long id, String content, String status, LocalDateTime created_at, LocalDateTime modified_at, Member member, Community community) {
        this.id = id;
        this.content = content;
        this.status = status;
        this.created_at = created_at;
        this.modified_at = modified_at;
        this.member = member;
        this.community = community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }

    public void update(String content, String status) {
        this.content = content;
        this.status = status;
        this.modified_at = LocalDateTime.now();
    }


}
