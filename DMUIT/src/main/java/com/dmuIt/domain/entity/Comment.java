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

    @Column(length = 15, nullable = false)
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

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public LocalDateTime getModified_at() {
        return modified_at;
    }

    public Member getMember() {
        return member;
    }

    public Community getCommunity() {
        return community;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public void setModified_at(LocalDateTime modified_at) {
        this.modified_at = modified_at;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }
}
