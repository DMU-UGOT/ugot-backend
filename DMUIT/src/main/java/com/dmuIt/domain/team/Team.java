package com.dmuIt.domain.team;

import com.dmuIt.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "team")
public class Team {
    //필드
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id", unique = true, nullable = false)
    private Long id;

    @Column(length = 15, nullable = false)
    private String teamName;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 50, nullable = false)
    private String content;

    @Column(length = 20, nullable = false)
    private String field;

    @Column(length = 20, nullable = false)
    private Integer personnel;

    @Column(length = 20, nullable = false)
    private List skill;

    @Column(length = 20, nullable = false)
    private String status;

    @Column(length = 20, nullable = false)
    private Date create_at;

    @Column(length = 20, nullable = false)
    private Date modified_at;

    @ManyToOne(fetch = FetchType.LAZY)// 다대일 관계, 하나의 classChange - 여러 member
    @JoinColumn(name = "member_id")
    private Member member;

    //빌더
    @Builder
    public Team(Long id, String teamName, String title, String content, String field,
                Integer personnel, List skill, String status,
                Date create_at, Date modified_at)
    {
        this.id = id;
        this.teamName = teamName;
        this.title = title;
        this.content = content;
        this.field = field;
        this.personnel = personnel;
        this.skill = skill;
        this.status = status;
        this.create_at = create_at;
        this.modified_at = modified_at;
    }


}