package com.planchella.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.planchella.enums.MembershipType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    @Setter(AccessLevel.NONE)
    private Long id;

    private String name;

    private String picUrl;

    private String accountUrl;

    private final List<Membership> memberships = new ArrayList<>();

    public User(Long id, String name, String picUrl, String accountUrl) {
        this.id = id;
        this.name = name;
        this.picUrl = picUrl;
        this.accountUrl = accountUrl;
    }

    public User() {
    }

    public static User getMockData() {
        return new User(1L, "John Smith", "www.pixabay.com/lol", "www.planchella.com/users/1");
    }

    public void updateByDelta(User newUserData) {
        if (newUserData != null) {
            if (newUserData.getName() != null)
                this.setName(newUserData.getName());
            if (newUserData.getPicUrl() != null)
                this.setPicUrl(newUserData.getPicUrl());
            if (newUserData.getAccountUrl() != null)
                this.setAccountUrl(newUserData.getAccountUrl());
        }

    }

    public void setPicUrl(String newPicUrl) {
        if (newPicUrl == null || newPicUrl.isBlank()) {
            throw new IllegalArgumentException("Profile Picture URL cannot be null or blank");
        }
        this.picUrl = newPicUrl;
    }

    public void setAccountUrl(String newAccountUrl) {
        if (newAccountUrl == null || newAccountUrl.isBlank()) {
            throw new IllegalArgumentException("Profile Picture URL cannot be null or blank");
        }
        this.accountUrl = newAccountUrl;
    }

    public List<Membership> getMemberships() {
        return List.copyOf(memberships);
    }

    public void addMembership(Long membership_id, Long community_id, MembershipType type) {
        Objects.requireNonNull(community_id, "Community cannot be null");
        Objects.requireNonNull(type, "Type cannot be null");

        // check if user has an existing membership in this community
        boolean exists = memberships.stream()
                .anyMatch(m -> m.getCommunity_id().equals(community_id));
        if (exists) {
            throw new IllegalStateException("User is already a member of this community");
        }
        memberships.add(new Membership(membership_id, this.id, community_id, type));
    }

    // Check if user can post in a community
    public boolean canPostIn(Long community_id) {
        return memberships.stream()
                .anyMatch(m -> m.getCommunity_id().equals(community_id) && m.canPost());
    }

    // Move user from one community to another with a role
    public void moveToCommunity(Long membership_id, Long oldCommunity_id, Long newCommunity_id, MembershipType role) {
        // Remove old membership if exists
        memberships.removeIf(m -> m.getCommunity_id().equals(oldCommunity_id) && m.canPost());
        // Add new membership
        memberships.add(new Membership(membership_id, this.id, newCommunity_id, role));
    }

    // Get primary membership (assuming one active membership per user)
    public Membership getCurrentMembership() {
        return memberships.isEmpty() ? null : memberships.getFirst();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", picUrl='" + picUrl + '\'' +
                ", name='" + name + '\'' +
                ", accountUrl='" + accountUrl + '\'' +
                '}';
    }
}
