package com.planchella.domain;

import com.planchella.enums.MembershipType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Community {

    private Long id;
    private String name;
    private List<Membership> memberships = new ArrayList<>();

    // Constructor
    public Community(String name){
        this.name = Objects.requireNonNull(name, "Community name cannot be null");
    }

    public Community(){}

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }

    public String getName(){return name; }
    public void setName(String name) {
        this.name = name;
    }


    public List<Membership> getMemberships(){
        return List.copyOf(memberships); // return final unmodifiable list
    }
    public void setMemberships(List<Membership> memberships) {
        this.memberships = memberships;
    }

    // Adding a user to this community with a specific role
    public void addMember(Long user_id, MembershipType type){
        Objects.requireNonNull(user_id,"User cannot be null");
        Objects.requireNonNull(type,"Role cannot be null");

        // check if user has an exisiting membership in this community
        boolean exists = memberships.stream()
                .anyMatch(m -> m.getUserID().equals(user_id));
        if(exists){
            throw new IllegalStateException("User is already a member of this community");
        }

        memberships.add(new Membership(0L, user_id, this.id, type));
    }

    // Remove a user from a specific community
    public void removeMember(Long user_id) {
        memberships.removeIf(m -> m.getUserID().equals(user_id));
    }

    // fetch all members with a specific role
    public List<Long> getMemberIDsByRole(MembershipType type) {
        return memberships.stream()
                .filter(m -> m.getRole() == type)
                .map(Membership::getUserID)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Community{" +
                "id=" + id +
                ", name=" + name + '\'' +
                ". memberships=" + memberships +
                '}';
    }
}