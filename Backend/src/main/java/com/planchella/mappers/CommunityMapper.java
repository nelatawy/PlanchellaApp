package com.planchella.mappers;

import com.planchella.DTOs.CommunityDTO;
import com.planchella.domain.Community;
import com.planchella.entities.CommunityEntity;
import org.hibernate.Session;

public class CommunityMapper {

    public static CommunityEntity domainToEntity(Community community, Session session) {
        CommunityEntity entity = new CommunityEntity();
        entity.setId(community.getId());
        entity.setName(community.getName());
        entity.setDescription(community.getDescription());
        entity.setCreatedAt(community.getCreatedAt());

        // Memberships managed separately through MembershipService
        // Not populated here - Community is a lightweight POJO

        return entity;
    }

    public static Community entityToDomain(CommunityEntity entity) {
        Community community = new Community(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getCreatedAt());

        // Memberships are now lazy-loaded via getMemberships(MembershipService)
        // This keeps the Community object lightweight and allows fresh data on demand

        return community;
    }

    public static CommunityDTO domainToDTO(Community community) {
        CommunityDTO communityDTO = new CommunityDTO();
        communityDTO.id = community.getId();
        communityDTO.name = community.getName();
        communityDTO.description = community.getDescription();
        communityDTO.memberCount = community.getMemberCount();
        communityDTO.createdAt = community.getCreatedAt();
        return communityDTO;
    }

    public static Community DTOtoDomain(CommunityDTO communityDTO) {
        Community community = new Community(
                communityDTO.id,
                communityDTO.name,
                communityDTO.description,
                communityDTO.createdAt);
        community.setMemberCount(communityDTO.memberCount);
        return community;
    }

}
