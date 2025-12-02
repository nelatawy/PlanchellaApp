package com.planchella.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MembershipEntity> getMemberships() {
        return memberships;
    }

    public void setMemberships(List<MembershipEntity> memberships) {
        this.memberships = memberships;
    }


    @Override
    public String toString() {
        return "Community{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
