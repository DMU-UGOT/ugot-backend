package com.dmuIt.domain.entity;
import com.dmuIt.global.audit.Auditable;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "community")
public class Community extends Auditable {
    //필드
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_id", unique = true, nullable = false)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 100, nullable = false)
    private String content;

    @Column
    private long viewCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)// 다대일 관계
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "community")
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Community(String title, String content, Integer viewCount) {
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }


    public void increaseViews() { this.viewCount++; }

}
