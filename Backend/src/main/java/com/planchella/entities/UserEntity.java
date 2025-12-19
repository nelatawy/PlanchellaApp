package com.planchella.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "pic_url")
    private String picUrl;

    @Column(name = "account_url")
    private String accountUrl;

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
    private List<MembershipEntity> memberships;

    public UserEntity(Long id, String picUrl, String name, String accountUrl, List<MembershipEntity> memberships) {
        this.id = id;
        this.name = name;
        this.picUrl = picUrl;
        this.accountUrl = accountUrl;
        this.memberships = memberships;
    }

    public UserEntity() {
    }

    public List<MembershipEntity> getMemberships() {
        return new ArrayList<>(memberships);
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
