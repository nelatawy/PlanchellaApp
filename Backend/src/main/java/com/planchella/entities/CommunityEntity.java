package com.planchella.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "communities")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class CommunityEntity {
    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private String createdAt;

    @OneToMany(mappedBy = "community", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH })
    private List<MembershipEntity> memberships;

    public CommunityEntity(Long id, String name, String description, String createdAt,
            List<MembershipEntity> memberships) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        if (memberships != null) {
            this.memberships = memberships;
        }
    }

    public CommunityEntity() {
    }

    public List<MembershipEntity> getMemberships() {
        return List.copyOf(memberships);
    }

    @Override
    public String toString() {
        return "Community{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
