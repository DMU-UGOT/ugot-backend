package com.dmuIt.domain.entity;

import com.dmuIt.global.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "team")
public class Team extends Auditable {
    //필드
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id", unique = true, nullable = false)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 50, nullable = false)
    private String content;

    @Column(length = 20, nullable = false)
    private String field;

    @Column(length = 20, nullable = false)
    private String _class;

    @Column(length = 20, nullable = false)
    private Integer personnel;

    @Column
    private long viewCount = 0;

    @Column
    private long bookmarked = 0;

}
