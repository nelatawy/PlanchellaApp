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

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_ids")
    private List<UserEntity> users;

    public List<UserEntity> getUsers() {
        return users;
    }

    public CommunityEntity(Long id, String name, List<UserEntity> users) {
        this.id = id;
        this.name = name;
        if(users != null){
            this.users = users;
        }
    }

    public CommunityEntity() {}

    @Override
    public String toString() {
        return "Community{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

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

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }
}
