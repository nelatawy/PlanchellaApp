package com.planchella.domain;

import com.planchella.enums.MembershipType;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Membership {

    private Long id;

    private final Long userId;

    private final Long communityId;

    private MembershipType type;

    // Constructor
    public Membership(Long id, Long userId, Long communityId, MembershipType type) {
        this.id = id;
        this.userId = Objects.requireNonNull(userId, "User cannot be null");
        this.communityId = Objects.requireNonNull(communityId, "Community cannot be null");
        this.type = Objects.requireNonNull(type, "Role cannot be null");
    }

    // Change the role/authority of this membership
    public void setType(MembershipType type) {
        this.type = Objects.requireNonNull(type, "Role cannot be null");
    }

    // Check if user is eligible to post in this community
    public boolean canPost() {
        // all roles can post but are adjustable based on future preference
        return type == MembershipType.MEMBER || type == MembershipType.TOP_CONTRIBUTOR || type == MembershipType.CREATOR;
    }

    @Override
    public String toString() {
        return "Membership{" +
                "user=" + userId +
                ", community=" + communityId +
                ", type=" + type +
                '}';

    }

}