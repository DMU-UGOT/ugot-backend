package com.dmuIt.domain.project;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "project")
public class Project {
    //필드
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "project_id", unique = true, nullable = false)
    private Long id;

    @Column(length = 15, nullable = false)
    private String projectName;

    @Column(length = 10, nullable = false)
    private Long member_id;

    @Column(length = 20, nullable = false)
    private Date create_at;

    @Column(length = 20, nullable = false)
    private Date modified_at;

    @Builder

    public Project(Long id, String projectName, Long member_id,
                   Date create_at, Date modified_at)
    {
        this.id = id;
        this.projectName = projectName;
        this.member_id = member_id;
        this.create_at = create_at;
        this.modified_at = modified_at;
    }
}
