package com.planchella.entities;

import com.planchella.enums.MembershipType;
import jakarta.persistence.*;

@Entity
public class MembershipEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToOne
    @JoinColumn(name = "community_id")
    private CommunityEntity community;

    private MembershipType type;

    public MembershipEntity(Long id, UserEntity user, CommunityEntity community, MembershipType type) {
        this.id =  id;
        this.user = user;
        this.community = community;
        this.type = type;
    }

    public MembershipEntity() {}

    @Override
    public String toString() {
        return "MembershipEntity{" +
                "id=" + id +
                ", user=" + user +
                ", community=" + community +
                '}';
    }

    public Long getId(){
        return id;
    }

    public UserEntity getUser() {
        return user;
    }
    public CommunityEntity getCommunity() {
        return community;
    }
    public MembershipType getType(){
        return type;
    }

}