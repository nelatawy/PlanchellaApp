package com.planchella.domain;

import com.planchella.entities.CommunityEntity;
import com.planchella.enums.MembershipType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
public class Community {

    @Setter(AccessLevel.NONE)
    private Long id;

    private String name;

    private List<Membership> memberships;

    // Constructor
    public Community(Long id, String name){
        this.id = id;
        this.name = Objects.requireNonNull(name, "Community name cannot be null");
        this.memberships = new ArrayList<>();
    }

    public Community(){}

    public void updateByDelta(Community newCommunityData) {
        if (newCommunityData != null) {
            this.id = newCommunityData.getId();
            this.name = newCommunityData.getName();
        }
    }

    public List<Membership> getMemberships(){
        return List.copyOf(memberships); // return final unmodifiable list
    }

    // Adding a user to this community with a specific role
    public void addMembership(Long membership_id, Long user_id, MembershipType type){
        Objects.requireNonNull(user_id,"User cannot be null");
        Objects.requireNonNull(type,"Role cannot be null");

        // check if user has an existing membership in this community
        boolean exists = memberships.stream()
                .anyMatch(m -> m.getUser_id().equals(user_id));
        if(exists){
            throw new IllegalStateException("User is already a member of this community");
        }
        memberships.add(new Membership(membership_id, user_id, this.id, type));
    }


    // Remove a user from a specific community
    public void removeMember(Long user_id) {
        memberships.removeIf(m -> m.getId().equals(user_id));
    }

    // fetch all members with a specific role
    public List<Long> getMemberIDsByRole(MembershipType type) {
        return memberships.stream()
                .filter(m -> m.getType() == type)
                .map(Membership::getUser_id)
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