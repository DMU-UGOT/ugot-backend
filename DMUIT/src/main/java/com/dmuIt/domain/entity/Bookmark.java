package com.dmuIt.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookmarkId;

    @Column
    private boolean checked;

    @Column
    private long memberId;

    @Column
    private long teamId;

    @ManyToOne(fetch = FetchType.LAZY)// 다대일 관계
    @JoinColumn(name = "member_id") // 포함 대상 정보는 member_id에 기록
    private Member member;
}
