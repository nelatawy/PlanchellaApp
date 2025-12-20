package com.planchella.mappers;

import com.planchella.DTOs.UserDTO;
import com.planchella.domain.User;
import com.planchella.domain.Membership;
import com.planchella.entities.MembershipEntity;
import com.planchella.entities.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;


public class UserMapper {

    public static UserEntity domainToEntity(User user, Session session) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setName(user.getName());
        entity.setAccountUrl(user.getAccountUrl());
        entity.setPicUrl(user.getPicUrl());
        entity.setEmail(user.getEmail());

        List<MembershipEntity> memberships = new ArrayList<>();
        for(Membership membership : user.getMemberships()) {
            MembershipEntity membershipEntity = session.getReference(MembershipEntity.class, membership.getId());
            memberships.add(membershipEntity);
        }
        entity.setMemberships(memberships);
        return entity;
    }

    public static User entityToDomain(UserEntity entity) {
        User user = new User(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getPicUrl(),
                entity.getAccountUrl()
        );
        for (MembershipEntity membershipEntity : entity.getMemberships()) {
            user.addMembership(membershipEntity.getId(), membershipEntity.getCommunity().getId(), membershipEntity.getType());
        }
        return user;
    }

    public static UserDTO domainToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.id = user.getId();
        userDTO.name = user.getName();
        userDTO.picUrl = user.getPicUrl();
        userDTO.accountUrl = user.getAccountUrl();
        return userDTO;
    }

    public static User DTOtoDomain(UserDTO userDTO) {

        return new User(
                userDTO.id,
                userDTO.name,
                userDTO.email,
                userDTO.picUrl,
                userDTO.accountUrl
        );
    }

}
