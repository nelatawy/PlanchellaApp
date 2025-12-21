package com.planchella.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Community {

    private Long id;

    private String name;

    private String description;

    private Long memberCount;

    private String createdAt;

    // Simple constructor
    public Community(Long id, String name, String description, String createdAt) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "Community name cannot be null");
        this.description = description;
        this.createdAt = createdAt;
    }

    public Community() {
    }

    public void updateByDelta(Community newCommunityData) {
        if (newCommunityData != null) {
            this.name = newCommunityData.getName() != null ? newCommunityData.getName() : this.name;
            this.description = newCommunityData.getDescription() != null ? newCommunityData.getDescription()
                    : this.description;
            // memberCount and createdAt are not modified by delta for safety
        }
    }

    // Community is now a pure POJO - no service dependencies
    // All membership-related operations moved to MembershipService

    @Override
    public String toString() {
        return "Community{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", memberCount=" + memberCount +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}