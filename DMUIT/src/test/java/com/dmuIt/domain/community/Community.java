package com.dmuIt.domain.community;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    private Integer viewcount;

    @Column(length = 10, nullable = false)
    private Integer votecount;

    @Column(length = 10, nullable = false)
    private Long member_id;

    @Column(length = 10, nullable = false)
    private String status;

    @Column(length = 20, nullable = false)
    private Date create_at;

    @Column(length = 20, nullable = false)
    private Date modified_at;


    @Builder
    public Community(Long id, String title, String content, Integer viewcount,
                     Integer votecount, Long member_id, String status, Date create_at,
                     Date modified_at)
    {
        this.id = id;
        this.title = title;
        this.content = content;
        this.viewcount = viewcount;
        this.votecount = votecount;
        this.member_id = member_id;
        this.status = status;
        this.create_at = create_at;
        this.modified_at = modified_at;
    }
}
