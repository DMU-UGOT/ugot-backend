package com.dmuIt.domain.comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {
    //필드
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "comment_id", unique = true, nullable = false)
    private Long id;

    @Column(length = 15, nullable = false)
    private String content;

    @Column(length = 10, nullable = false)
    private Long member_id;

    @Column(length = 10, nullable = false)
    private Long team_id;

    @Column(length = 10, nullable = false)
    private Long study_id;

    @Column(length = 10, nullable = false)
    private Long community_id;

    @Column(length = 10, nullable = false)
    private String status;

    @Column(length = 20, nullable = false)
    private Date create_at;

    @Column(length = 20, nullable = false)
    private Date modified_at;


    @Builder

    public Comment(Long id, String content, Long member_id, Long team_id, Long study_id,
                   Long community_id, String status, Date create_at, Date modified_at)
    {
        this.id = id;
        this.content = content;
        this.member_id = member_id;
        this.team_id = team_id;
        this.study_id = study_id;
        this.community_id = community_id;
        this.status = status;
        this.create_at = create_at;
        this.modified_at = modified_at;
    }
}
