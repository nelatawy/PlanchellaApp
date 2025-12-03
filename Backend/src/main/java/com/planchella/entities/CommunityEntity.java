package com.planchella.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class CommunityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_ids")
    private List<MembershipEntity> memberships;


    public CommunityEntity(Long id, String name, List<MembershipEntity> memberships) {
        this.id = id;
        this.name = name;
        if(memberships != null){
            this.memberships = memberships;
        }
    }

    public CommunityEntity() {}


    public List<MembershipEntity> getMemberships() {
        return List.copyOf(memberships);
    }


    @Override
    public String toString() {
        return "Community{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
