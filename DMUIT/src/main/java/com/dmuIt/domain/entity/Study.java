package com.dmuIt.domain.entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "study")
public class Study {
    //필드
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_id", unique = true, nullable = false)
    private Long id;

    @Column(length = 15, nullable = false)
    private String title;

    @Column(length = 60, nullable = false)
    private String content;

    @Column(length = 15, nullable = false)
    private String field;

    @Column(length = 15, nullable = false)
    private String status;

    @Column(length = 20, nullable = false)
    private Date create_at;

    @Column(length = 20, nullable = false)
    private Date modified_at;


    @OneToMany(mappedBy = "study") // 일대다관계
    private List<Comment> comments;

    @ManyToOne(fetch = FetchType.LAZY)// 다대일 관계
    @JoinColumn(name = "member_id")
    private Member members;



    @Builder
    public Study(Long id, String title, String content, String field,
                 String status, Date create_at, Date modified_at)
    {
        this.id = id;
        this.title = title;
        this.content = content;
        this.field = field;
        this.status = status;
        this.create_at = create_at;
        this.modified_at = modified_at;
    }
}