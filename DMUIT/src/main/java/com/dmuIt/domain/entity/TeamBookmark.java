package com.dmuIt.domain.entity;

import com.dmuIt.global.audit.Auditable;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
public class TeamBookmark extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookmarkId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)// 다대일 관계
    @JoinColumn(name = "member_id") // 포함 대상 정보는 member_id에 기록
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @Column(nullable = false)
    private boolean status;

    public TeamBookmark(Team team, Member member) {
        this.team = team;
        this.member = member;
        this.status = true;
    }

    public void unBookmark(Team team) {
        this.status = false;
        team.setBookmarked(team.getBookmarked() - 1);
    }
}
