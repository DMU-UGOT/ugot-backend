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
@Table(name = "classChange")
public class ClassChange extends Auditable {
    //필드
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "classChange_id", unique = true, nullable = false)
    private Long classChangeId;

    @Column(nullable = false)
    private String grade;

    @Column(nullable = false)
    private String currentClass;

    @Column(nullable = false)
    private String changeClass;

    @Column(nullable = false)
    private String status = "가능";

    @ManyToOne(fetch = FetchType.LAZY)// 다대일 관계
    @JoinColumn(name = "member_id") // 포함 대상 정보는 member_id에 기록
    private Member member;
}