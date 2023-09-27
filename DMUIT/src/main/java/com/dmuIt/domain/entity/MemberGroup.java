package com.dmuIt.domain.entity;

import com.dmuIt.global.audit.Auditable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class MemberGroup extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberGroupId;


    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private MemberGroup.RoleInGroup role = RoleInGroup.USER;
    public void setMember(Member member) {
        this.member = member;
        if (!this.member.getMemberGroups().contains(this)) {
            this.member.getMemberGroups().add(this);
        }
    }

    public void setGroup(Group group) {
        this.group = group;
        if (!this.group.getMemberGroups().contains(this)) {
            this.group.getMemberGroups().add(this);
        }
    }

    @Getter
    public enum RoleInGroup {
        ADMIN("관리자"),
        USER("사용자");

        private final String status;

        RoleInGroup(String status) {
            this.status = status;
        }
    }
}
