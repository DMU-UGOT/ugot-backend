package com.dmuIt.domain.entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "classChange")
public class ClassChange {
    //필드
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "classChange_id", unique = true, nullable = false)
    private Long id;

    @Column(length = 15, nullable = false)
    private String title;

    @Column(length = 15, nullable = false)
    private String content;

    @Column(length = 15, nullable = false)
    private String status;

    @Column(length = 20, nullable = false)
    private Date create_at;

    @Column(length = 20, nullable = false)
    private Date modified_at;

    @ManyToOne(fetch = FetchType.LAZY)// 다대일 관계
    @JoinColumn(name = "member_id") // 포함 대상 정보는 member_id에 기록
    private Member member;

    @Builder

    public ClassChange(Long id, String title, String content, String status,
                       Date create_at, Date modified_at, Member member)
    {
        this.id = id;
        this.title = title;
        this.content = content;
        this.status = status;
        this.create_at = create_at;
        this.modified_at = modified_at;
        this.member = member;
    }
}