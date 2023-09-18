package com.dmuIt.domain.entity;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "favorite")
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long likeId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "group_id")
    private Group group;


    @Builder
    public Favorite(Long likeId, Group group) {
        this.likeId = likeId;
        //this.member = member;
        this.group = group;
    }

    public void Like(Group group){
        this.group = group;
    }
}
