package com.planchella.entities;

import com.planchella.enums.MembershipType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "memberships")
@Getter
@Setter
public class MembershipEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "community_id")
    private CommunityEntity community;

    @Column(name = "membership_type")
    private MembershipType type;

    public MembershipEntity(Long id, UserEntity user, CommunityEntity community, MembershipType type) {
        this.id = id;
        this.user = user;
        this.community = community;
        this.type = type;
    }

    public MembershipEntity() {
    }

    @Override
    public String toString() {
        return "MembershipEntity{" +
                "id=" + id +
                ", user=" + user +
                ", community=" + community +
                '}';
    }

}