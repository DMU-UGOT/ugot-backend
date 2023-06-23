package com.dmuIt.domain.entity;

import com.dmuIt.domain.entity.Community;
import com.dmuIt.domain.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "vote")
public class Vote {
    //필드
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "vote_id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id")
    private Community community;


}
