package com.planchella.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name ;
    private String picUrl ;
    private String accountUrl;

    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "membership_id")
    private List<MembershipEntity> memberships;

    public UserEntity(Long id, String picUrl, String name, String accountUrl, List<MembershipEntity> memberships) {
        this.id = id;
        this.name = name;
        this.picUrl = picUrl;
        this.accountUrl = accountUrl;
        this.memberships = memberships;
    }
    public UserEntity() {}


    public List<MembershipEntity> getMemberships() {
        return memberships;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", picUrl='" + picUrl + '\'' +
                ", name='" + name + '\'' +
                ", accountUrl='" + accountUrl + '\'' +
                '}';
    }

}
