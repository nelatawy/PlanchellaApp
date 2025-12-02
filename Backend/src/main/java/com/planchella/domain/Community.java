package com.planchella.domain;

import com.planchella.enums.MembershipType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Community {

    private Long id;
    private String name;
    private final List<Membership> memberships = new ArrayList<>();

    // Constructor
    public Community(String name){
        this.name = Objects.requireNonNull(name, "Community name cannot be null");
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getName(){return name; }

    public List<Membership> getMemberships(){
        return List.copyOf(memberships); // return final unmodifiable list
    }

    // Adding a user to this community with a specific role
    public void addMember(User user, MembershipType type){
        Objects.requireNonNull(user,"User cannot be null");
        Objects.requireNonNull(type,"Role cannot be null");

        // check if user has an exisiting membership in this community
        boolean exists = memberships.stream()
                .anyMatch(m -> m.getUser().equals(user));
        if(exists){
            throw new IllegalStateException("User is already a member of this community");
        }

        memberships.add(new Membership(user, this, type));
    }

    // Remove a user from a specific community
    public void removeMember(User user) {
        memberships.removeIf(m -> m.getUser().equals(user));
    }

    // fetch all members with a specific role
    public List<User> getMemberByRole(MembershipType type) {
        return memberships.stream()
                .filter(m -> m.getRole() == type)
                .map(Membership::getUser)
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