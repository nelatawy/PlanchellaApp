package com.planchella.mappers;

import com.planchella.DTOs.UserDTO;
import com.planchella.domain.User;
import com.planchella.entities.UserEntity;
import org.hibernate.Session;


public class UserMapper {

    public static UserEntity domainToEntity(User user, Session session) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setName(user.getName());
        entity.setAccountUrl(user.getAccountUrl());
        entity.setPicUrl(user.getPicUrl());
        entity.setEmail(user.getEmail());

        // Memberships managed separately through MembershipService
        // Not populated here - User is a lightweight POJO

        return entity;
    }

    public static User entityToDomain(UserEntity entity) {
        User user = new User(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getPicUrl(),
                entity.getAccountUrl());

        // Memberships are now lazy-loaded via getMemberships(MembershipService)
        // This keeps the User object lightweight and allows fresh data on demand

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
                userDTO.accountUrl);
    }

}
