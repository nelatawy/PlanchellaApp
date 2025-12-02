package com.planchella.entities;

import jakarta.persistence.*;

@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name ;
    private String picUrl ;
    private String accountUrl;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "membership_id")
    private MembershipEntity membership;

    public UserEntity(Long id, String picUrl, String name, String accountUrl, MembershipEntity membership) {
        this.id = id;
        this.name = name;
        this.picUrl = picUrl;
        this.accountUrl = accountUrl;
        this.membership = membership;
    }
    public UserEntity() {}

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", picUrl='" + picUrl + '\'' +
                ", name='" + name + '\'' +
                ", accountUrl='" + accountUrl + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public String getPicUrl() {
        return picUrl;
    }

    public String getAccountUrl() {
        return accountUrl;
    }

    public MembershipEntity getMembership() {
        return membership;
    }

}
