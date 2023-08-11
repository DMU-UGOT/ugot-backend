package com.dmuIt.domain.entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "community")
public class Community {
    //필드
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_id", unique = true, nullable = false)
    private Long id;

    @Column(length = 15, nullable = false)
    private String title;

    @Column(length = 15, nullable = false)
    private String content;

    @Column(length = 10, nullable = false)
    private Integer viewCount;

    @Column(length = 10, nullable = false)
    private Integer voteCount;

    @Column(length = 10, nullable = false)
    private Long member_id;

    @Column(nullable = true)
    private char deleteYN;

    @Column(length = 10, nullable = false)
    private String status;

    @Column(length = 20, nullable = false)
    private LocalDateTime create_at;

    @Column(length = 20, nullable = false)
    private LocalDateTime modified_at;

//    @OneToMany(mappedBy = "community")
//    private List<Vote> vote;
//    private List<Comment> comment;



    @Builder
    public Community(String title, String content, Integer viewCount, Integer voteCount, Long member_id, char deleteYN, String status, LocalDateTime create_at, LocalDateTime modified_at) {
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.voteCount = voteCount;
        this.member_id = member_id;
        this.deleteYN = deleteYN;
        this.status = status;
        this.create_at = create_at;
        this.modified_at = modified_at;
    }

    public void update(String title, String content, Long member_id, String status, LocalDateTime create_at) {
        this.title = title;
        this.content = content;
        this.member_id = member_id;
        this.status = status;
        this.create_at = create_at;
        this.modified_at = LocalDateTime.now();
    }
}
