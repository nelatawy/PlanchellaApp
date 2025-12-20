package com.planchella.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Community {

    private Long id;

    private String name;

    // Simple constructor
    public Community(Long id, String name) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "Community name cannot be null");
    }

    public Community() {
    }

    public void updateByDelta(Community newCommunityData) {
        if (newCommunityData != null) {
            this.id = newCommunityData.getId();
            this.name = newCommunityData.getName();
        }
    }

    // Community is now a pure POJO - no service dependencies
    // All membership-related operations moved to MembershipService

    @Override
    public String toString() {
        return "Community{" +
                "id=" + id +
                ", name=" + name + '\'' +
                '}';
    }
}