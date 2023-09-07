package com.dmuIt.domain.entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "community")
public class Community {
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

    @Column
    private long voteCount = 0;

    @Column(length = 10, nullable = false)
    private Long member_id;

    @Column
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column
    private LocalDateTime modified_at;

    /*@OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    @Where(clause = "parent_id is null")
    private List<Comment> commentList = new ArrayList<>();*/

//    @OneToMany(mappedBy = "community")
//    private List<Vote> vote;
//    private List<Comment> comment;



    @Builder
    public Community(String title, String content, Integer viewCount, Long member_id) {
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.member_id = member_id;
    }

    public void update(String title, String content, Long member_id) {
        this.title = title;
        this.content = content;
        this.member_id = member_id;
        this.modified_at = LocalDateTime.now();
    }


    public void increaseViews() { this.viewCount++; }

}
