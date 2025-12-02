package com.planchella.domain;

import com.planchella.enums.MembershipType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {

    private Long id;
    private String name ;
    private String picUrl ;
    private String accountUrl;
    private final List<Membership> memberships = new ArrayList<>();


    public User(String picUrl, String name, String accountUrl) {
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.picUrl = Objects.requireNonNull(picUrl, "Pic url cannot be null");
        this.accountUrl = Objects.requireNonNull(accountUrl, "Account url cannot be null");
    }

    public User() {}

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", picUrl='" + picUrl + '\'' +
                ", name='" + name + '\'' +
                ", accountUrl='" + accountUrl + '\'' +
                '}';
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {this.id = id;}

    public String getName() {
        return name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public String getAccountUrl() {
        return accountUrl;
    }

    public List<Membership> getMemberships() {
        return List.copyOf(memberships);
    }

    // Change profile picture
    public void changeProfilePicture(String newPicUrl){
        if (newPicUrl == null || newPicUrl.isBlank()){
            throw new IllegalArgumentException("Profile Picture URL cannot be null or blank");
        }
        this.picUrl = newPicUrl;
    }

    // Check if user can post in a community
    public boolean canPostIn(Community community) {
        return memberships.stream()
                .anyMatch(m -> m.getCommunity().equals(community) && m.canPost());
    }

    // Move user from one community to another with a role
    public void moveToCommunity(Community oldCommunity, Community newCommunity, MembershipType role){
        // Remove old membership if exists
        memberships.removeIf(m -> m.getCommunity().equals(oldCommunity) && m.canPost());
        // Add new membership
        memberships.add(new Membership(this, newCommunity, role));
            }

    // Get primary membership (assuming one active membership per user)
    public Membership getCurrentMembership(){
        return memberships.isEmpty() ? null : memberships.getFirst();
    }

    // public boolean canPost(Community community, List<Membership> memberships){
    // return memberships.stream()}
    //.anyMatch()
    // private list<Memberships> memberships;



}
