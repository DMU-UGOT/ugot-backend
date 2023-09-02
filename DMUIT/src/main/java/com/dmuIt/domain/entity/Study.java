package com.dmuIt.domain.entity;
import com.dmuIt.global.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "study")
public class Study extends Auditable {
    //필드
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_id", unique = true, nullable = false)
    private Long studyId;

    @Column(length = 15, nullable = false)
    private String title;

    @Column(length = 60, nullable = false)
    private String content;

    @Column(nullable = false)
    private String isContact;

    @Column(length = 20, nullable = false)
    private Integer allPersonnel;

    @Column
    private Integer nowPersonnel = 1;

//    @OneToMany(mappedBy = "study") // 일대다관계
//    private List<Comment> comments;

    @Column
    private String kakaoOpenLink;

    @Column(nullable = false)
    private String gitHubLink;

    @Column
    private long viewCount = 0;

    @Column
    private long bookmarked = 0;

    @ManyToOne(fetch = FetchType.LAZY)// 다대일 관계
    @JoinColumn(name = "member_id")
    private Member members;
}