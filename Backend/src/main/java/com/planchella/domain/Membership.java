package com.planchella.domain;

import com.planchella.enums.MembershipType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Membership {

    @Setter(AccessLevel.NONE)
    private final Long id;

    private final Long user_id;

    private final Long community_id;

    private MembershipType type;

    // Constructor
    public Membership(Long id, Long user_id, Long community_id, MembershipType type) {
        this.id = id;
        this.user_id = Objects.requireNonNull(user_id, "User cannot be null");
        this.community_id = Objects.requireNonNull(community_id, "Community cannot be null");
        this.type = Objects.requireNonNull(type, "Role cannot be null");
    }


    // Change the role/authority of this membership
    public void setType(MembershipType type){
        this.type = Objects.requireNonNull(type,"Role cannot be null");
    }

    // Check if user is eligible to post in this community
    public boolean canPost(){
        //all roles can post but are adjustable based on future preference
        return type == MembershipType.MEMBER || type  == MembershipType.TOP_CONTRIBUTOR;
    }

    @Override
    public String toString(){
        return "Membership{" +
                "user=" + user_id +
                ", community=" + community_id+
                ", type=" + type +
                '}';

    }

}