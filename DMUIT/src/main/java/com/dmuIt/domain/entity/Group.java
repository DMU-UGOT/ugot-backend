package com.dmuIt.domain.entity;

import com.dmuIt.global.audit.Auditable;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@SequenceGenerator(name = "GROUP_SEQ_GEN",
        sequenceName = "GROUP_SEQ", //매핑할 데이터베이스 시퀀스 이름
        initialValue = 1,
        allocationSize = 1)
@Table(name = "_group")
public class Group extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "GROUP_SEQ")
    private Long groupId;

    @Column(length = 30, nullable = false)
    private String groupName;

    @Column(length = 30, nullable = false)
    private Integer person;

    @Column(length = 50, nullable = false)
    private String githubUrl;

    @Builder
    public Group(Long groupId, String groupName, Integer person, String githubUrl) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.person = person;
        this.githubUrl = githubUrl;
    }
}
