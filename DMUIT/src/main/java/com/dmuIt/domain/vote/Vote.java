package com.dmuIt.domain.vote;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "vote")
public class Vote {
    //필드
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "vote_id", unique = true, nullable = false)
    private Long id;

    @Column(length = 10, nullable = false)
    private Long member_id;

    @Column(length = 10, nullable = false)
    private Long community_id;


    @Builder
    public Vote(Long id, Long member_id, Long community_id) {
        this.id = id;
        this.member_id = member_id;
        this.community_id = community_id;
    }
}
