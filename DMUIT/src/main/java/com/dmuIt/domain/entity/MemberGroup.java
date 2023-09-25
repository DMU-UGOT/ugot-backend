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
}
