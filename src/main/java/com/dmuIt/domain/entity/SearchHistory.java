package com.dmuIt.domain.entity;

import com.dmuIt.global.audit.Auditable;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchHistory extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long searchHistoryId;

    @Column(nullable = false)
    private String keyword;

    @Column(nullable = false)
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static SearchHistory of(String keyword, String type, Member member) {
        return SearchHistory.builder()
                .keyword(keyword)
                .type(type)
                .member(member)
                .build();
    }
}
