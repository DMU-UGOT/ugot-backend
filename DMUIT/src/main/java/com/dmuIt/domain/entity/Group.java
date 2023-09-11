package com.dmuIt.domain.entity;

import com.dmuIt.global.audit.Auditable;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "group")
public class Group extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String groupName;

    @Column(length = 30, nullable = false)
    private Integer person;

    @Column(length = 50, nullable = false)
    private String githubUrl;
    @Builder
    public Group(Long id, String groupName, Integer person, String githubUrl) {
        this.id = id;
        this.groupName = groupName;
        this.person = person;
        this.githubUrl = githubUrl;
    }
}
