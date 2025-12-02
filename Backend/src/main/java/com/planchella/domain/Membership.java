package com.planchella.domain;

import com.planchella.enums.MembershipType;

import java.util.Objects;

public class Membership {

    private final User user;
    private final Community community;
    private MembershipType type;

    // Constructor
    public Membership(User user, Community community, MembershipType type) {
        this.user = Objects.requireNonNull(user, "User cannot be null");
        this.community = Objects.requireNonNull(community, "Community cannot be null");
        this.type = Objects.requireNonNull(type, "Role cannot be null");
    }

    @Override
    public String toString(){
        return "Membership{" +
                "user=" + user.getName() +
                ", community=" + community.getName() +
                ", type=" + type +
                '}';

    }

    public User getUser() {
        return user;
    }

    public Community getCommunity() {
        return community;
    }

    public MembershipType getRole() {
        return type;
    }

    // Change the role/authority of this membership
    public void changeRole(MembershipType type){
        this.type = Objects.requireNonNull(type,"Role cannot be null");
    }

    // Check if user is eligible to post in this community
    public boolean canPost(){
        //all roles can post but are adjustable based on future prefrence
        return type == MembershipType.MEMBER || type  == MembershipType.TOP_CONTRIBUTOR;
    }

}